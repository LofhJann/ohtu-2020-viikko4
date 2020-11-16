package ohtu.verkkokauppa;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KauppaTest {

    private Pankki pankki;
    private Viitegeneraattori viite;
    private Varasto varasto;
    private Kauppa k;



    @Before
    public void setUp() {
        // luodaan ensin mock-oliot
        pankki = mock(Pankki.class);

        viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(2)).thenReturn(15);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(1, "leipä", 10));
        when(varasto.saldo(3)).thenReturn(0);
        when(varasto.haeTuote(3)).thenReturn(new Tuote(1, "powerking", 1000));

        // sitten testattava kauppa
        k = new Kauppa(varasto, pankki, viite);
    }

    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(),anyInt());
        // toistaiseksi ei välitetty kutsussa käytetyistä parametreista
    }

    @Test
    public void ostoksetLaskutetaanOikealtaAsiakkaalta() {
        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(5));
        // toistaiseksi ei välitetty kutsussa käytetyistä parametreista
    }

    @Test
    public void useampiTuoteToimii() {
        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(15));
        // toistaiseksi ei välitetty kutsussa käytetyistä parametreista
    }

    @Test
    public void useampiSamaTuoteToimii() {
        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(10));
        // toistaiseksi ei välitetty kutsussa käytetyistä parametreista
    }

    @Test
    public void tuoteOnLoppunut() {
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(3);
        k.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(5));
    }

    @Test
    public void asioinninAlussaTilaOnTyhjä() {
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.aloitaAsiointi();
        k.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), eq(0));
    }

    @Test
    public void viitegeneraattoriLuoAinaUudenViitteen() {
        k.aloitaAsiointi();
        k.tilimaksu("pekka", "12345");
        k.tilimaksu("pekka", "12345");
        verify(viite, times(2)).uusi();
    }

    @Test
    public void poistaKoristaToimii() {
        Tuote tuote = varasto.haeTuote(1);
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.poistaKorista(1);

        verify(varasto, times(1)).palautaVarastoon(tuote);
    }
}