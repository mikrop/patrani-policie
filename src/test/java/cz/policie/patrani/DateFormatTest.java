package cz.policie.patrani;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Testování práce s datumem.
 */
public class DateFormatTest {

    /**
     * Formátuje předaný datum do {@link DateFormat#CLASSIC}.
     *
     * @param source formátované datum
     * @return výsledek po formátování
     */
    private static String parse(String source) {
        Date date = ScraperUtils.parse(source);
        return DateFormat.CLASSIC.formatClassic(date);
    }

    @Test
    public void parse() {
        Assert.assertEquals("18.06.2016", DateFormatTest.parse("18.6.2016"));
        Assert.assertEquals("21.11.2014", DateFormatTest.parse("21. listopadu 2014"));
    }

}