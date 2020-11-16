
package ohtu.intjoukkosovellus;

import java.util.Arrays;

public class IntJoukko {

    public static final int VAKIO_KOKO = 5;
    public static final int VAKIO_KASVATUSKOKO = 5;
    private final int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] lukujono;      // Joukon luvut säilytetään taulukon alkupäässä.
    private int alkioidenMaara;    // Tyhjässä joukossa alkioiden_määrä on nolla.

    public IntJoukko() {
        this(VAKIO_KOKO, VAKIO_KASVATUSKOKO);
    }

    public IntJoukko(int koko) {
        this(koko, VAKIO_KASVATUSKOKO);

    }

    public IntJoukko(int koko, int kasvatuskoko) {
        if (koko < 0) {
            throw new IllegalArgumentException("Koko on alle 0");
        }
        if (kasvatuskoko < 0) {
            throw new IllegalArgumentException("Kasvatuskoko on alle 0");
        }
        lukujono = new int[koko];
        Arrays.fill(lukujono, 0);
        alkioidenMaara = 0;
        this.kasvatuskoko = kasvatuskoko;

    }

    public boolean lisaaLuku(int luku) {

        if (!sisaltaaLuvun(luku)) {
            lukujono[alkioidenMaara] = luku;
            alkioidenMaara++;
            kasvataLukujonoa();
            return true;
        }
        return false;
    }

    private void kasvataLukujonoa() {
        if (alkioidenMaara % lukujono.length == 0) {
            int[] taulukkoOld = lukujono;
            kopioiTaulukko(lukujono, taulukkoOld);
            lukujono = new int[alkioidenMaara + kasvatuskoko];
            kopioiTaulukko(taulukkoOld, lukujono);
        }
    }

    public boolean sisaltaaLuvun(int luku) {
        for (int i = 0; i < alkioidenMaara; i++) {
            if (luku == lukujono[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean poista(int luku) {
        int kohta = -1;

        if (!sisaltaaLuvun(luku)) {
            return false;
        }

        for (int i = 0; i < lukujono.length; i++) {
            if (lukujono[i] == luku) {
                lukujono[i] = 0;
                kohta = i;
            }
        }

        for (int j = kohta; j < alkioidenMaara - 1; j++) {
            int lukujononLuku = lukujono[j];
            lukujono[j] = lukujono[j + 1];
            lukujono[j + 1] = lukujononLuku;
        }
        alkioidenMaara--;
        return true;


    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        for (int i = 0; i < vanha.length; i++) {
            uusi[i] = vanha[i];
        }

    }

    public int getAlkioidenMaara() {
        return alkioidenMaara;
    }


    @Override
    public String toString() {

        StringBuilder merkkijono = new StringBuilder("{");
        for (int i = 0; i < alkioidenMaara; i++) {
            merkkijono.append(lukujono[i]);
            if (i < alkioidenMaara-1) {
                merkkijono.append(", ");
            }
        }
        merkkijono.append("}");
        return merkkijono.toString();
    }

    public int[] toIntArray() {
        return Arrays.copyOf(lukujono, alkioidenMaara);
    }


    public static IntJoukko yhdiste(IntJoukko joukkoA, IntJoukko joukkoB) {
        IntJoukko yhdiste = new IntJoukko();

        for (int luku : joukkoA.toIntArray()) {
            yhdiste.lisaaLuku(luku);
        }
        for (int luku : joukkoB.toIntArray()) {
            yhdiste.lisaaLuku(luku);
        }
        return yhdiste;
    }

    public static IntJoukko leikkaus(IntJoukko joukkoA, IntJoukko joukkoB) {
        IntJoukko leikkaus = new IntJoukko();
        for (int luku : joukkoA.toIntArray()) {
            if (joukkoB.sisaltaaLuvun(luku)) {
                leikkaus.lisaaLuku(luku);
            }
        }
        return leikkaus;

    }

    public static IntJoukko erotus(IntJoukko joukkoA, IntJoukko joukkoB) {
        IntJoukko erotus = new IntJoukko();
        for (int luku : joukkoA.toIntArray()) {
            if (!joukkoB.sisaltaaLuvun(luku)) {
                erotus.lisaaLuku(luku);
            }
        }

        return erotus;
    }

}
