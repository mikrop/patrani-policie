package cz.policie.patrani.osoby;

import cz.policie.patrani.PatraniSoup;
import cz.policie.patrani.model.HledanaOsoba;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * Parsuje aplikaci policii české republiky http://aplikace.policie.cz/patrani-osoby/Vyhledavani.aspx
 */
public class OsobyScraper {

    // Adresa webového formuláře
    private static final String URL = "http://aplikace.policie.cz/patrani-osoby/Vyhledavani.aspx";

    /**
     * Z předané URL parsuje detail osoby do struktury {@link HledanaOsoba}.
     *
     * @param url odkaz na webovou stránku s detail o hledané osobě
     * @return {@link HledanaOsoba}
     * @throws IOException chyba parsování
     */
    private static HledanaOsoba detail(String url) throws IOException {

        Document detail = Jsoup.connect(url).get();
        Elements table = detail.select("table[id=ctl00_ctl00_Application_BasePlaceHolder_TablePatros]");
        Iterator<Element> trs = table.select("tr").iterator();
        Map<String, String> map = new LinkedHashMap<>();
        while (trs.hasNext()) {
            Elements span = trs.next().select("td span");
            map.put(span.attr("id"), span.text());
        }
        return HledanaOsoba.parse(map);

    }

    /**
     * Seznam hledaných osob odpovídajících předanému formuláři.
     *
     * @param  jmenoPrijmeni jméno a příjmení oddělené mezerou
     * @return seznam hledaných osob
     * @throws IOException chyba parsování
     */
    static List<HledanaOsoba> parse(String jmenoPrijmeni) throws IOException {

        Connection.Response vyhledavani = PatraniSoup
                .connect(URL)
                .data("ctl00$ctl00$Application$BasePlaceHolder$ddlPohlavi", "MUŽ")
                .data("ctl00$ctl00$Application$BasePlaceHolder$ddlHledanyPohresovany", "any")
                .data("ctl00$ctl00$Application$BasePlaceHolder$txtJmenoPrijmeni", jmenoPrijmeni)
                .data("ctl00$ctl00$Application$BasePlaceHolder$ddlBydliste", "any")
                .data("ctl00$ctl00$Application$BasePlaceHolder$ddlObcanstvi", "any")
                .data("ctl00$ctl00$Application$BasePlaceHolder$txtVekOd", "")
                .data("ctl00$ctl00$Application$BasePlaceHolder$txtVekDo", "")
                .data("ctl00$ctl00$Application$BasePlaceHolder$txtVyskaOd", "")
                .data("ctl00$ctl00$Application$BasePlaceHolder$txtVyskaDo", "")
                .data("ctl00$ctl00$Application$BasePlaceHolder$ddlBarvaOci", "any")
                .data("ctl00$ctl00$Application$BasePlaceHolder$ddlBarvaVlasu", "any")
                .data("ctl00$ctl00$Application$BasePlaceHolder$btnVyhledat", "Vyhledat")
                .method(Connection.Method.POST)
                .execute();

        Document galery = Jsoup
                .connect("http://aplikace.policie.cz/patrani-osoby/Gallery.aspx")
                .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0")
                .method(Connection.Method.GET)
                .cookies(vyhledavani.cookies())
                .get();

        Iterator<Element> boxs = galery.select("div[class=personBox]").iterator();
        List<HledanaOsoba> result = new ArrayList<>();
        while (boxs.hasNext()) {
            Element next = boxs.next();
            Element link = next.select("td a").first();
            HledanaOsoba osoba = detail(link.attr("abs:href"));

            result.add(osoba);
        }
        return result;

    }

}
