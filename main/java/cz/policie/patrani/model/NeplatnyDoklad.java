package cz.policie.patrani.model;

import org.junit.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class NeplatnyDoklad {

    // Formát data od neplatnosti dokladu
    private static final DateFormat DF = new SimpleDateFormat("dd.MM.yyyy");

    private String cislo;
    private TypDoklad typDoklad;
    private String serie;
    private Date neplatnyOd;

    public static NeplatnyDoklad parse(Map<String, String> map) throws ParseException {
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
                doklad.neplatnyOd = DF.parse(value);
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
