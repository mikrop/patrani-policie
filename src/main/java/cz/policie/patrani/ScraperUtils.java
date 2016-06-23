package cz.policie.patrani;

import cz.policie.patrani.model.Pohlavi;
import org.junit.Assert;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * Pomocné metody pro parsování aplikace http://aplikace.policie.cz
 */
public abstract class ScraperUtils {

    // Vzor o nedohledání
    private static final Pattern RANGE_PATTERN = Pattern.compile("(\\d+) až (\\d+)");

    /**
     * Parsuje předaný datum do objektu {@link Date}.
     *
     * @param source datum k parsování
     * @return datum odpovídající předanému řetězci
     */
    public static Date parse(String source) {
        Assert.assertNotNull(source);
        return DateFormat.parse(source);
    }

    /**
     * Transformuje hodnotu ANO/NE na {@link Boolean}.
     *
     * @param anotherString očekávaná hodnota ANO/NE
     * @return {@link true} pokud bude předáno ANO, jinak {@link false}
     */
    public static boolean toBoolean(String anotherString) {
        Assert.assertNotNull(anotherString);
        return anotherString.contains("ANO");
    }

    /**
     * Transformuje výčtovou hodnotu na základě předaného řetězce, který pokud obsahuje "muž",
     * vrací {@link Pohlavi#MUZ}, jinak {@link Pohlavi#ZENA}.
     *
     * @param lblPohlavi parsovaný řetězec
     * @return výčtová hodnota
     */
    public static Pohlavi toPohlavi(String lblPohlavi) {
        Assert.assertNotNull(lblPohlavi);
        return Pattern.compile("muž").matcher(lblPohlavi).find() ? Pohlavi.MUZ : Pohlavi.ZENA;
    }

}
