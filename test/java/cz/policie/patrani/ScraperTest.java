package cz.policie.patrani;

import cz.policie.patrani.model.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * http://jsoup.sourcearchive.com/documentation/1.6.1-2/UrlConnectTest_8java_source.html
 */
public class ScraperTest {

    // Vstupní parametry testovací
    private static final String TESTOVACI_SPZ = "8B67354";
    private static final String TESTOVACI_JMENO_PRIJMENI = "LAKATOS+Herman";
    private static final String TESTOVACI_IMEI = "12345678901234";
    private static final String TESTOVACI_CISLO_DOKLAD = "37305581";

    @Test
    public void testVozidlaScraper() throws Exception {
        List<OdcizeneVozidlo> vozidla = VozidlaScraper.parse(TESTOVACI_SPZ);
        Assert.assertEquals(vozidla.iterator().next().getRokVyroby(), Integer.valueOf(2014));
    }

    @Test
    public void testOsobyScraper() throws Exception {
        HledanaOsoba osoba = new HledanaOsoba();
        osoba.setJmenoPrijmeni(TESTOVACI_JMENO_PRIJMENI);

        List<HledanaOsoba> osoby = OsobyScraper.parse(osoba);
        Assert.assertEquals(osoby.size(), 1);
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
