package cz.policie.patrani.model;

import cz.policie.patrani.ScraperUtils;
import org.junit.Assert;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

public class NeplatnyDoklad {

    private String cislo;
    private TypDoklad typDoklad;
    private String serie;
    private Date neplatnyOd;

    public static NeplatnyDoklad parse(Map<String, String> map) {
        Assert.assertNotNull(map);

        NeplatnyDoklad doklad = new NeplatnyDoklad();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String value = entry.getValue().isEmpty() ? null : entry.getValue();
            if (entry.getKey().equals("cislo")) {
                doklad.cislo = value;
            } else if (entry.getKey().equals("typDoklad")) {
                doklad.typDoklad = TypDoklad.byCaption(value);
            } else if (entry.getKey().equals("serie")) {
                doklad.serie = value;
            } else if (entry.getKey().equals("neplatnyOd")) {
                doklad.neplatnyOd = ScraperUtils.parse(value);
            }
        }
        return doklad;
    }

    public String getCislo() {
        return cislo;
    }

    public TypDoklad getTypDoklad() {
        return typDoklad;
    }

    public String getSerie() {
        return serie;
    }

    public Date getNeplatnyOd() {
        return neplatnyOd;
    }

}
