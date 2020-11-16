
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

    public int mahtavuus() {
        return alkioidenMaara;
    }


    @Override
    public String toString() {
        if (alkioidenMaara == 0) {
            return "{}";
        } else if (alkioidenMaara == 1) {
            return "{" + lukujono[0] + "}";
        } else {
            String tuotos = "{";
            for (int i = 0; i < alkioidenMaara - 1; i++) {
                tuotos += lukujono[i];
                tuotos += ", ";
            }
            tuotos += lukujono[alkioidenMaara - 1];
            tuotos += "}";
            return tuotos;
        }
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenMaara];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = lukujono[i];
        }
        return taulu;
    }


    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko x = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            x.lisaaLuku(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            x.lisaaLuku(bTaulu[i]);
        }
        return x;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko y = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            for (int j = 0; j < bTaulu.length; j++) {
                if (aTaulu[i] == bTaulu[j]) {
                    y.lisaaLuku(bTaulu[j]);
                }
            }
        }
        return y;

    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko z = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            z.lisaaLuku(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            z.poista(bTaulu[i]);
        }

        return z;
    }

}
