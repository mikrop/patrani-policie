package cz.policie.patrani;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class ScraperUtilsTest {

    private SimpleDateFormat simpleDateFormat;

    @Before
    public void setUp() throws Exception {
        this.simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    }

    @Test
    public void parse() throws Exception {
        Assert.assertEquals("18.06.2016", simpleDateFormat.format(ScraperUtils.parse("18.6.2016")));
        Assert.assertEquals("21.11.2014", simpleDateFormat.format(ScraperUtils.parse("21. listopadu 2014")));
    }

}