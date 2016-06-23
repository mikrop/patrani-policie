package cz.policie.patrani;

import org.junit.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Výčtová hodnota datumů, které umíme parsovat.
 */
enum DateFormat {

    CLASSIC("dd.MM.yyyy"),
    LONG("dd. MMMM yyyy")
    ;

    private java.text.DateFormat formatter;

    private DateFormat(String pattern) {
        this.formatter = new SimpleDateFormat(pattern);
    }

    /**
     * Formatuje datum "klasickým způsobem"
     *
     * @param date datum pro formatovani
     * @return čitelný datum
     */
    public String formatClassic(Date date) {
        Assert.assertNotNull(date);
        return CLASSIC.formatter.format(date);
    }

    /**
     * @see ScraperUtils#parse(String)
     */
    static Date parse(String source) {

        // parsovani mesice je zavisle na locale a nastaveni operacniho systemu
        // proto rucne nahradime ceske nazvy mesicu za cislovky
        String replace = source.replace("ledna", "1.")
                .replace("února", "2.")
                .replace("března", "3.")
                .replace("dubna", "4.")
                .replace("května", "5.")
                .replace("června", "6.")
                .replace("července", "7.")
                .replace("srpna", "8.")
                .replace("září", "9.")
                .replace("října", "10.")
                .replace("listopadu", "11.")
                .replace("prosince", "12.");

        for (DateFormat value : DateFormat.values()) {
            try {
                return value.formatter.parse(replace);
            } catch (ParseException e) {
                // Další pokus
            }
        }
        throw new IllegalArgumentException("Nepodporovaný format předaného data " + source);
    }

}
