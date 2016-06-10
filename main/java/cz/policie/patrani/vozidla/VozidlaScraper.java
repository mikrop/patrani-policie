package cz.policie.patrani.vozidla;

import cz.policie.patrani.PatraniSoup;
import cz.policie.patrani.model.OdcizeneVozidlo;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * Parsuje aplikaci policii české republiky http://aplikace.policie.cz/patrani-vozidla/default.aspx
 */
public class VozidlaScraper {

    // Adresa webového formuláře
    private static final String URL = "http://aplikace.policie.cz/patrani-vozidla/default.aspx";

    /**
     * Z předané URL parsuje detail vozidla do struktury {@link OdcizeneVozidlo}.
     *
     * @param url odkaz na webovou stránku s detail o odcizeném vozidla
     * @return {@link OdcizeneVozidlo}
     * @throws IOException chyba parsování
     */
    private static OdcizeneVozidlo detail(String url) throws IOException {

        Document detail = Jsoup.connect(url).get();
        Elements table = detail.select("table[id=searchTableResults]");
        Iterator<Element> trs = table.select("tr").iterator();
        Map<String, String> map = new LinkedHashMap<>();
        while (trs.hasNext()) {
            Elements span = trs.next().select("td span");
            map.put(span.attr("id"), span.text());
        }
        return OdcizeneVozidlo.parse(map);

    }

    /**
     * Seznam vozidel odpovídajících předané registrační značce/VINu.
     *
     * @param query registrační značka, nebo VIN hledaného vozidla
     * @return seznam nalezených vozidel
     * @throws IOException chyba parsování
     */
    static List<OdcizeneVozidlo> parse(String query) throws IOException {

        Document doc = PatraniSoup
                .connect(URL)
                .data("ctl00$Application$txtSPZ", query)
                .data("ctl00$Application$txtVIN", query)
                .data("ctl00$Application$cmdHledej", "Vyhledat")
                .method(Connection.Method.POST)
                .get();

        Elements table = doc.select("table[id=celacr]");
        Iterator<Element> trs = table.select("tr[class=registracni-znacky]").iterator();
        List<OdcizeneVozidlo> result = new ArrayList<>();
        while (trs.hasNext()) {
            Element next = trs.next();
            Element link = next.select("td a").first();
            OdcizeneVozidlo vozidlo = detail(link.attr("abs:href"));

            result.add(vozidlo);
        }
        return result;

    }

}
