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
    private static final String TESTOVACI_JMENO_PRIJMENI = "PAVEL";
    private static final String TESTOVACI_IMEI = "12345678901234";
    private static final String TESTOVACI_DOKLAD_CISLO = "108520090";

    @Test
    public void testVozidlaScraper() throws Exception {
        List<OdcizeneVozidlo> vozidla = VozidlaScraper.parse(TESTOVACI_SPZ);
    }

    @Test
    public void testOsobyScraper() throws Exception {
        Osoba osoba = new Osoba();
        osoba.setJmenoPrijmeni(TESTOVACI_JMENO_PRIJMENI);

        OsobyPage page = OsobyScraper.parse(osoba);
        Set<HledanaOsoba> hledaneOsoby = page.getHledaneOsoby();
        while (page.hasNext()) {
            OsobyPage next = page.next();
            hledaneOsoby = next.getHledaneOsoby();
        }
    }

//    @Test
//    public void testMobilyScraper() throws Exception {
//        OdcizenyTelefon telefon = MobilyScraper.parse(TESTOVACI_IMEI);
//    }
//
//    @Test
//    public void testDokladyScraper() throws Exception {
//        List<NeplatnyDoklad> doklady = DokladyScraper.parse(TESTOVACI_DOKLAD_CISLO, TypDoklad.OBCANSKY_PRUKAZ);
//    }

}
