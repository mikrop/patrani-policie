package cz.policie.patrani;

import cz.policie.patrani.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ScraperTest {

    // Vstupní parametry testovací
    private static final String TESTOVACI_SPZ = "8B67354";
    private static final String TESTOVACI_JMENO_PRIJMENI = "BALOG+DANIEL";
    private static final String TESTOVACI_IMEI = "12345678901234";
    private static final String TESTOVACI_CISLO_DOKLAD = "37305581";

    @Test
    public void testVozidlaScraper() throws Exception {
        List<OdcizeneVozidlo> vozidla = VozidlaScraper.parse(TESTOVACI_SPZ);
        Assert.assertEquals(vozidla.iterator().next().getRokVyroby(), Integer.valueOf(2014));
    }

    @Test
    public void testOsobyScraper() throws Exception {
        List<HledanaOsoba> osoby = OsobyScraper.parse(TESTOVACI_JMENO_PRIJMENI);
    }

//    @Test
//    public void testMobilyScraper() throws Exception {
//        OdcizenyTelefon telefon = MobilyScraper.parse(TESTOVACI_IMEI);
//    }

    @Test
    public void testDokladyScraper() throws Exception {
        List<NeplatnyDoklad> doklady = DokladyScraper.parse(TESTOVACI_CISLO_DOKLAD, TypDoklad.FIALOVY_PAS);
        Assert.assertEquals(doklady.iterator().next().getCislo(), TESTOVACI_CISLO_DOKLAD);
    }

}
