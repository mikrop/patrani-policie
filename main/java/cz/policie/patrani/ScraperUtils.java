package cz.policie.patrani;

import cz.policie.patrani.model.Pohlavi;
import org.jsoup.select.Elements;
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
    private static final Pattern NOT_FOUND_PATTERN = Pattern.compile(" nebyl nalezen ");
    private static final Pattern RANGE_PATTERN = Pattern.compile("(\\d+) až (\\d+)");
    private static final String[] DF_PATTERNS = { "dd.MM.yyyy", "dd. MMMM yyyy" };

    /**
     * Vrací {@code true}, pokud je v předaném elementu řetězec {@link #NOT_FOUND_PATTERN}, jinak {@code false}
     *
     * @param elements testovaný element, jehož text budeme prohledávat
     * @return příznak, zda byl text nalezen
     */
    static boolean findNotFound(Elements elements) {
        Assert.assertNotNull(elements);
        String text = elements.text();
        return NOT_FOUND_PATTERN.matcher(text).find();
    }

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

}
