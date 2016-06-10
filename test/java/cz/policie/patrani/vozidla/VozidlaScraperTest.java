package cz.policie.patrani.vozidla;

import cz.policie.patrani.model.OdcizeneVozidlo;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class VozidlaScraperTest {

    // Testovací SPZka
    private static final String TESTOVACI_SPZ = "8B67354";

    @Test
    public void testParse() throws Exception {
        List<OdcizeneVozidlo> vozidla = VozidlaScraper.parse(TESTOVACI_SPZ);
        Assert.assertEquals(vozidla.iterator().next().getRokVyroby(), Integer.valueOf(2014));
    }

}
