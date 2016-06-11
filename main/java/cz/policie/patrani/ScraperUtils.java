package cz.policie.patrani;

import org.jsoup.select.Elements;
import org.junit.Assert;

import java.util.regex.Pattern;

/**
 * Pomocné metody pro parsování aplikace http://aplikace.policie.cz
 */
abstract class ScraperUtils {

    // Vzor o nedohledání
    private static final Pattern NOT_FOUND_PATTERN = Pattern.compile(" nebyl nalezen ");

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

}
