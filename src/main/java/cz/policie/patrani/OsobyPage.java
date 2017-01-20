package cz.policie.patrani;

import cz.policie.patrani.model.HledanaOsoba;
import cz.policie.patrani.model.Osoba;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;

/**
 * Parsuje webové stránky policie české republiky
 * <a href="http://aplikace.policie.cz/patrani-osoby/Vyhledavani.aspx">pátrání po osobách</a>.
 * Seznam osob lze získat voláním metody {@link OsobyPage#getHledaneOsoby()} a další stránku pak
 * metodou {@link OsobyPage#next()}.
 *
 * <pre>
 * {@code
 *  OsobyPage page = new OsobyPage(osoba);
 *  Set<HledanaOsoba> hledaneOsoby = page.getHledaneOsoby();
 *  while (page.hasNext()) {
 *      OsobyPage next = page.next();
 *      hledaneOsoby = next.getHledaneOsoby();
 *  }
 * }
 * </pre>
 */
public class OsobyPage implements Iterator<OsobyPage> {

    // Adresa webového formuláře
    private static final String VYHLEDAVANI_URL = "http://aplikace.policie.cz/patrani-osoby/Vyhledavani.aspx";
    // Adresa fotografie hledané osoby
    private static final String IMAGE_URL = "http://aplikace.policie.cz/patrani-osoby/ViewImage.aspx";

    private Set<HledanaOsoba> hledaneOsoby = new LinkedHashSet<>();
    private String galleryUrl;
    private Map<String, String> cookies;

    /**
     * Pokusí se dohledat předanou osobu dle předaného filtru {@link Osoba}, které lze získat volání metody
     * {@link OsobyPage#getHledaneOsoby()}. Pokud bude nalezeno osob, je možné získat další a další volání metody {@link OsobyPage#next()}
     *
     * @param osoba vstupní filtr
     * @throws IOException pokud při dohledávání osoby v aplikaci policie české republiky
     */
    public OsobyPage(Osoba osoba) throws IOException {

        Connection.Response response = PatraniSoup
                .connect(VYHLEDAVANI_URL)
                .method(Connection.Method.POST)
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
                .followRedirects(true) // Hooodně důležitej parametr
                .execute();

        this.cookies = response.cookies();
        parse(response);
    }

    /**
     * Kolekce vyparsovaných osob.
     *
     * @return hledané osoby ve struktuře {@link HledanaOsoba}
     */
    public Set<HledanaOsoba> getHledaneOsoby() {
        return hledaneOsoby;
    }

    /**
     * Vrací {@code true}, pokud parsovaná stránka obsahuje odkaz
     * <pre>
     * {@code
     *  div[id=PagingRepeater1BottomPager] a:contains(Další)
     * }
     * </pre> na další stránku v výsledky hledání.
     * @return {@code false} pokud nebyl odkaz nalezen
     */
    @Override
    public boolean hasNext() {
        return !galleryUrl.isEmpty();
    }

    /**
     * Další stránka k parsování.
     *
     * @return {@link OsobyPage}
     */
    @Override
    public OsobyPage next() {
        try {

            Connection.Response response = PatraniSoup
                    .connect(galleryUrl)
                    .method(Connection.Method.GET)
                    .cookies(cookies)
                    .execute();

            return parse(response);

        } catch (IOException e) {
            throw new IllegalStateException("Chyba parsování dalších hledaných osoby. " + e);
        }
    }

    /**
     * Z předané URL parsuje detail osoby do struktury {@link HledanaOsoba}.
     *
     * @param url odkaz na webovou stránku s detail o hledané osobě
     * @return {@link HledanaOsoba}
     * @throws IOException chyba parsování
     */
    private HledanaOsoba detail(String url) throws IOException {

        Document detail = Jsoup
                .connect(url)
                .get();

        Elements table = detail.select("table[id=ctl00_ctl00_Application_BasePlaceHolder_TablePatros]");

        Map<String, String> map = new LinkedHashMap<>();
        map.put(HledanaOsoba.DETAIL_URL_KEY, url);
        map.put(HledanaOsoba.IMG_SRC_KEY, table.select("img").first().absUrl("src"));

        Element tr = table.select("tr").first();
        Iterator<Element> spans = tr.select("td span").iterator();
        while (spans.hasNext()) {
            Element next = spans.next();
            map.put(next.attr("id"), next.text());
        }
        return HledanaOsoba.parse(map);

    }

    /**
     * Parsuje z předaného {@link Connection.Response} strukturu hledaných osob.
     *
     * @param response zdroj
     * @return {@link OsobyPage}
     * @throws IOException
     */
    private OsobyPage parse(Connection.Response response) throws IOException {

        Document galery = response.parse();
        Elements label1 = galery.select("div[id=gallery]:contains(Nenalezen)");
        if (!label1.isEmpty()) {
            throw new PatraniNotFoundException(label1);
        }

        Iterator<Element> boxs = galery.select("div[class=personBox]").iterator();
        while (boxs.hasNext()) {
            Element next = boxs.next();
            Element link = next.select("a").first();
            HledanaOsoba hledanaOsoba = detail(link.attr("abs:href"));

            hledaneOsoby.add(hledanaOsoba);
        }

        this.galleryUrl = galery
                .select("div[id=PagingRepeater1BottomPager] a:contains(Další)")
                .attr("abs:href");
        int endIndex = galleryUrl.indexOf("&");
        if (endIndex != -1) {
            galleryUrl = galleryUrl.substring(0, endIndex);
        }

        return this;
    }

}
