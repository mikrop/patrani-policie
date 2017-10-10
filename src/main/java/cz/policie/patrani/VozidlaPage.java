package cz.policie.patrani;

import cz.policie.patrani.model.OdcizeneVozidlo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * Parsuje webové stránky policie české republiky
 * <a href="http://aplikace.policie.cz/patrani-vozidla/default.aspx">pátrání po odcizených vozidlech</a>.
 *
 * <pre>
 * {@code
 *  List<OdcizeneVozidlo> vozidla = VozidlaPage.parse("8B67354");
 * }
 * </pre>
 */
public class VozidlaPage {

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
        map.put(OdcizeneVozidlo.DETAIL_URL_KEY, url);
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
    public static List<OdcizeneVozidlo> parse(String query) throws IOException {

        Document doc = PatraniSoup
                .connect(URL)
                .data("ctl00$Application$txtSPZ", query)
                .data("ctl00$Application$txtVIN", query)
                .data("ctl00$Application$cmdHledej", "Vyhledat")
                .post();

        Elements label1 = doc.select("span[id=ctl00_Application_lblOutput]:contains(nebyl nalezen)");
        if (!label1.isEmpty()) {
            throw new PatraniNotFoundException(label1);
        }

        Elements table = doc.select("table[id=celacr]");
        Elements trs = table.select("tr");
        List<OdcizeneVozidlo> result = new ArrayList<>();
        for (int i = 1; i < trs.size(); i++) {
            Element link = trs.get(i).select("td a").first();
            OdcizeneVozidlo vozidlo = detail(link.attr("abs:href"));

            result.add(vozidlo);
        }
        return result;

    }

}
