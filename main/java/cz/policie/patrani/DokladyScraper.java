package cz.policie.patrani;

import cz.policie.patrani.model.NeplatnyDoklad;
import cz.policie.patrani.model.TypDoklad;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Parsuje aplikaci policii české republiky http://aplikace.mvcr.cz/neplatne-doklady/default.aspx
 */
public class DokladyScraper {

    // Adresa webového formuláře
    private static final String URL = "http://aplikace.mvcr.cz/neplatne-doklady/default.aspx";

    /**
     * Z předaného řádku tabulky parsuje detail neplatného dokladu do struktury {@link NeplatnyDoklad}.
     *
     * @param tr řádek tabulky s neplatného dokladu
     * @return {@link NeplatnyDoklad}
     * @throws IOException chyba parsování
     */
    private static NeplatnyDoklad detail(Element tr) throws IOException, ParseException {

        Elements tds = tr.select("td");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("cislo", tds.get(0).text());
        map.put("typDoklad", tds.get(1).text());
        map.put("serie", tds.get(2).text());
        map.put("neplatnyOd", tds.get(3).text());
        return NeplatnyDoklad.parse(map);

    }

    /**
     * Pokusí se dohladat doklad v databázi neplatných dokladů.
     *
     * @param cislo identifikátor hledaného dokladu
     * @param typDokladu typ dokladu
     * @return neplatný doklad
     * @throws IOException chyba parsování
     */
    static List<NeplatnyDoklad> parse(String cislo, TypDoklad typDokladu) throws IOException, ParseException {

        Document doc = PatraniSoup
                .connect(URL)
                .data("ctl00$Application$txtCisloDokladu", cislo)
                .data("ctl00$Application$ddlTypDokladu", Objects.toString(typDokladu.getValue()))
                .data("ctl00$Application$cmdZobraz", "Vyhledat")
                .method(Connection.Method.POST)
                .get();

        Elements lblEvidence = doc.select("span[id=ctl00_Application_lblEvidence]");
        if (ScraperUtils.findNotFound(lblEvidence)) {
            throw new PatraniNotFoundException(lblEvidence.text());
        }

        Elements table = doc.select("table[id=ctl00_Application_DataGrid1]");
        Iterator<Element> trs = table.select("tr[class=even],tr[class=odd]").iterator();
        List<NeplatnyDoklad> result = new ArrayList<>();
        while (trs.hasNext()) {
            NeplatnyDoklad doklad = detail(trs.next());
            result.add(doklad);
        }
        return result;

    }

}
