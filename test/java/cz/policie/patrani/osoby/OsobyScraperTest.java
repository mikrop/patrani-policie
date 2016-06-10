package cz.policie.patrani.osoby;

import cz.policie.patrani.model.HledanaOsoba;
import cz.policie.patrani.model.OdcizeneVozidlo;
import cz.policie.patrani.vozidla.VozidlaScraper;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class OsobyScraperTest {

    // Testovací jméno a příjmení
    private static final String TESTOVACI_JMENO_PRIJMENI = "BALOG+DANIEL";

    @Test
    public void testParse() throws Exception {
        List<HledanaOsoba> osoby = OsobyScraper.parse(TESTOVACI_JMENO_PRIJMENI);
        Assert.assertTrue(true);
    }

}
