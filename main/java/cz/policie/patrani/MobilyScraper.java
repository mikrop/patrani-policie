package cz.policie.patrani;

import cz.policie.patrani.model.OdcizenyTelefon;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Parsuje aplikaci policii české republiky http://aplikace.policie.cz/patrani-mobily/default.aspx
 */
public class MobilyScraper {

    // Adresa webového formuláře
    private static final String URL = "http://aplikace.policie.cz/patrani-mobily/default.aspx";

    /**
     * Dohledná předaný IMEI v databázi odcizených mobilních zařízení.
     *
     * @param imei IMEI odcizeného mobilního telefomu
     * @return nalezený mobilní telefon
     * @throws IOException chyba parsování
     */
    static OdcizenyTelefon parse(String imei) throws IOException {

        Document doc = PatraniSoup
                .connect(URL)
                .data("ctl00$Application$tbImei", imei)
                .data("ctl00$Application$Button1", "Vyhledat")
                .method(Connection.Method.POST)
                .get();

        Elements label1 = doc.select("span[id=ctl00_Application_Label1]");
        if (ScraperUtils.findNotFound(label1)) {
            throw new PatraniNotFoundException(label1.text());
        }
        return new OdcizenyTelefon();
    }

}
