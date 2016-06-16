package cz.policie.patrani;

import cz.policie.patrani.model.*;
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

    private static final String VYHLEDAVANI_URL = "http://aplikace.policie.cz/patrani-osoby/Vyhledavani.aspx";
    private static final String GALLERY_URL = "http://aplikace.policie.cz/patrani-osoby/Gallery.aspx";

    private static Map<String, String> COOKIES; // TODO: 17.6.2016 Pouze pro účely testování
    static {
        COOKIES = new HashMap<>();
        COOKIES.put("ASP.NET_SessionId", "x4qaa3ejfxvjuv55sgtsnr45");
    }

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
        Element tr = table.select("tr").first();
        Iterator<Element> spans = tr.select("td span").iterator();
        Map<String, String> map = new LinkedHashMap<>();
        while (spans.hasNext()) {
            Element next = spans.next();
            map.put(next.attr("id"), next.text());
        }
        return HledanaOsoba.parse(map);

    }

    /**
     * Seznam hledaných osob odpovídajících předanému formuláři.
     *
     * @param osoba hledaná osoba
     * @return seznam hledaných osob
     * @throws IOException chyba parsování
     */
    static List<HledanaOsoba> parse(HledanaOsoba osoba) throws IOException {

        Connection.Response response = Jsoup.connect(VYHLEDAVANI_URL)
                .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0")
                .method(Connection.Method.GET)
                .execute();

        Document doc = response.parse();
        String viewstate = doc.select("input[name=__VIEWSTATE]").attr("value");
        String viewstategenerator = doc.select("input[name=__VIEWSTATEGENERATOR]").attr("value");
        String eventvalidation = doc.select("input[name=__EVENTVALIDATION]").attr("value");

        Connection.Response vyhledavani = Jsoup.connect(VYHLEDAVANI_URL)
                .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0")
                .method(Connection.Method.POST)
                .data("__EVENTTARGET", "")
                .data("__EVENTARGUMENT", "")
                .data("__VIEWSTATE", viewstate)
                .data("__VIEWSTATEGENERATOR", viewstategenerator)
                .data("__EVENTVALIDATION", eventvalidation)
                .data("ctl00$ctl00$Application$BasePlaceHolder$ddlPohlavi", osoba.getPohlavi().getValue())
                .data("ctl00$ctl00$Application$BasePlaceHolder$ddlHledanyPohresovany", osoba.getHledanyPohresovany().getValue())
                .data("ctl00$ctl00$Application$BasePlaceHolder$txtJmenoPrijmeni", osoba.getJmenoPrijmeni())
                .data("ctl00$ctl00$Application$BasePlaceHolder$ddlBydliste", osoba.getBydliste())
                .data("ctl00$ctl00$Application$BasePlaceHolder$ddlObcanstvi", osoba.getObcanstvi())
                .data("ctl00$ctl00$Application$BasePlaceHolder$txtVekOd", osoba.getVekOd())
                .data("ctl00$ctl00$Application$BasePlaceHolder$txtVekDo", osoba.getVekDo())
                .data("ctl00$ctl00$Application$BasePlaceHolder$txtVyskaOd", osoba.getVyskaOd())
                .data("ctl00$ctl00$Application$BasePlaceHolder$txtVyskaDo", osoba.getVyskaDo())
                .data("ctl00$ctl00$Application$BasePlaceHolder$ddlBarvaOci", osoba.getBarvaOci().getValue())
                .data("ctl00$ctl00$Application$BasePlaceHolder$ddlBarvaVlasu", osoba.getBarvaVlasu().getValue())
                .data("ctl00$ctl00$Application$BasePlaceHolder$btnVyhledat", "Vyhledat")
                .followRedirects(false) // Hooodně důležitej parametr
                .execute();

        if (vyhledavani.statusCode() != 302) {
            throw new IllegalStateException("Nepodařilo se odeslat formulář hledané osoby.");
        }

        Document galery = Jsoup.connect(GALLERY_URL)
                .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0")
                .method(Connection.Method.GET)
//                .cookies(vyhledavani.cookies()) // TODO: 17.6.2016 Odkomentovat
                .cookies(COOKIES)
                .get();

        Iterator<Element> boxs = galery.select("div[class=personBox]").iterator();
        List<HledanaOsoba> result = new ArrayList<>();
        while (boxs.hasNext()) {
            Element next = boxs.next();
            Element link = next.select("a").first();
            osoba = detail(link.attr("abs:href"));

            result.add(osoba);
        }
        return result;

    }

}
