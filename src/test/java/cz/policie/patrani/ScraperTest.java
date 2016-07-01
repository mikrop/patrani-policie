package cz.policie.patrani;

import cz.policie.patrani.model.*;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * Příklady použití.
 */
public class ScraperTest {

    // Vstupní parametry testovací
    private static final String TESTOVACI_SPZ = "8B67354";
    private static final String TESTOVACI_JMENO_PRIJMENI = "A ALI ESSAM MOHAMMED";
    private static final String TESTOVACI_IMEI = "12345678901234";
    private static final String TESTOVACI_DOKLAD_CISLO = "108520090";

    public void testVozidla() throws Exception {
        List<OdcizeneVozidlo> vozidla = VozidlaPage.parse(TESTOVACI_SPZ);
    }

    public void testOsoby() throws Exception {
        Osoba osoba = new Osoba();
        osoba.setJmenoPrijmeni(TESTOVACI_JMENO_PRIJMENI);

        OsobyPage page = new OsobyPage(osoba);
        Set<HledanaOsoba> hledaneOsoby = page.getHledaneOsoby();
        while (page.hasNext()) {
            OsobyPage next = page.next();
            hledaneOsoby = next.getHledaneOsoby();
        }
    }

    public void testMobily() throws Exception {
        OdcizenyTelefon telefon = MobilyPage.parse(TESTOVACI_IMEI);
    }

    public void testDoklady() throws Exception {
        List<NeplatnyDoklad> doklady = DokladyPage.parse(TESTOVACI_DOKLAD_CISLO, TypDoklad.OBCANSKY_PRUKAZ);
    }

}
