package cz.policie.patrani;

import cz.policie.patrani.model.OdcizenyTelefon;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Parsuje webové stránky policie české republiky o <a href="http://aplikace.policie.cz/patrani-mobily/default.aspx">odcizených mobilních zařízeních</a>
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
                .post();

        Elements label1 = doc.select("span[id=ctl00_Application_Label1]:contains(nebyl nalezen)");
        if (!label1.isEmpty()) {
            throw new PatraniNotFoundException(label1);
        }
        return new OdcizenyTelefon();

    }

}
