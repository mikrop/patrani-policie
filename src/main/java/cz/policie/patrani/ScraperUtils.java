package cz.policie.patrani;

import cz.policie.patrani.model.Pohlavi;
import org.junit.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Pomocné metody pro parsování aplikace http://aplikace.policie.cz
 */
public abstract class ScraperUtils {

    // Vzor o nedohledání
    private static final Pattern RANGE_PATTERN = Pattern.compile("(\\d+) až (\\d+)");
    private static final String[] DF_PATTERNS = { "dd.MM.yyyy", "dd. MMMM yyyy" };

    /**
     * Parsuje předaný datum do objektu {@link Date}.
     *
     * @param value datum k parsování
     * @return datum odpovídající předanému řetězci
     */
    public static Date parse(String value) {
        Assert.assertNotNull(value);

        for (String pattern : DF_PATTERNS) {
            try {
                DateFormat df = new SimpleDateFormat(pattern);
                return df.parse(value);
            } catch (ParseException e) {
                // Další pokus
            }
        }
        throw new IllegalArgumentException("Nepodporovaný format předaného data " + value);
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
