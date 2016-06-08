package cz.policie.patrani.osoby;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;

import java.io.IOException;

/**
 * @author Michal Hájek, <a href="mailto:michal.hajek@marbes.cz">michal.hajek@marbes.cz</a>
 * @since 8. 6. 2016
 */
public class Test {

    // Testovací SPZ
    private static final String query = "8B67354";

    public static void main(String[] args) throws IOException {

        Connection.Response form = Jsoup
                .connect("http://aplikace.policie.cz/patrani-vozidla/default.aspx")
                .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0")
                .method(Connection.Method.GET)
                .execute();

        Document src = form.parse();
        String viewstate = src.select("input[name=__VIEWSTATE]").attr("value");
        String eventvalidation = src.select("input[name=__EVENTVALIDATION]").attr("value");

        Document dst = Jsoup
                .connect("http://aplikace.policie.cz/patrani-vozidla/default.aspx")
                .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0")
                .data("__EVENTTARGET", "")
                .data("__EVENTARGUMENT", "")
                .data("__VIEWSTATE", viewstate)
                .data("__EVENTVALIDATION", eventvalidation)
                .data("ctl00$Application$txtSPZ", query)
                .data("ctl00$Application$txtVIN", query)
                .data("ctl00$Application$cmdHledej", "Vyhledat")
                .data("ctl00$Application$CurrentPage", "1")
                .cookies(form.cookies())
                .post();

        Assert.assertNotNull(dst.select("table[id=celacr]"));

    }

}
