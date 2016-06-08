package cz.policie.patrani.vozidla;

import cz.policie.patrani.model.OdcizeneVozidlo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;

import java.io.IOException;
import java.util.*;

/**
 * Parsuje aplikaci policii české republiky http://aplikace.policie.cz/patrani-vozidla/default.aspx
 */
public class Test {

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
    public static List<OdcizeneVozidlo> parse(String query) throws IOException {

        String url = "http://aplikace.policie.cz/patrani-vozidla/default.aspx?__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=%2FwEPDwULLTEzNzIzMjY0MDMPZBYCZg9kFgICBw9kFgICAQ9kFgoCBA8PZBYCHgpvbmtleXByZXNzBTZyZXR1cm4gb25LZXlwcmVzcyhldmVudCwnY3RsMDBfQXBwbGljYXRpb25fY21kSGxlZGVqJylkAgYPD2QWAh8ABTZyZXR1cm4gb25LZXlwcmVzcyhldmVudCwnY3RsMDBfQXBwbGljYXRpb25fY21kSGxlZGVqJylkAhQPDxYEHgRUZXh0BRxDZWxrb3bDvSBwb8SNZXQgesOhem5hbcWvOiAxHgdWaXNpYmxlZ2RkAhUPDxYCHwJoZGQCGQ8PFgIfAQU%2FRGF0YWLDoXplIGJ5bGEgbmFwb3NsZWR5IGFrdHVhbGl6b3bDoW5hIDxiPjYuIHByb3NpbmNlIDIwMTI8L2I%2BZGRkE2qlXWNJcxoc8%2FLZOQEi5oKrGzs%3D&__EVENTVALIDATION=%2FwEWBQL80qOCBwLQsb3%2BBgK9peeFDwLv%2BPyjBAL4oIjjDVvi8FJppOBh8gjuF1u%2Ft7viEDtA&ctl00%24Application%24txtSPZ="
                + query + "&ctl00%24Application%24txtVIN=" + query
                + "&ctl00%24Application%24cmdHledej=Vyhledat&ctl00%24Application%24CurrentPage=1";

        Document doc = Jsoup.connect(url).get();
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

    public static void main(String[] args) throws IOException {

        String spz = "8B67354";
        List<OdcizeneVozidlo> vozidla = Test.parse(spz);
        Assert.assertEquals(vozidla.iterator().next().getRokVyroby(), Integer.valueOf(2014));

    }

}
