package cz.policie.patrani.model;

import org.junit.Assert;

import java.util.Map;

public class HledanaOsoba {

    private String jmenoPrijmeni;
    private String pohlavi;
    private String datumNarozeni;
    private String patraniStart;
    private String bydliste;
    private boolean nebezpecny;
    private boolean ozbrojen;
    private boolean nakazlivy;
    private String statniPrislusnost;
    private String vyska;
    private String zdanliveStari;
    private String popis;
    private String dalsiUdaje;

    private HledanaOsoba() {
    }

    public static HledanaOsoba parse(Map<String, String> map) {
        Assert.assertNotNull(map);

        HledanaOsoba osoba = new HledanaOsoba();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String value = entry.getValue().isEmpty() ? null : entry.getValue();
            if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lbl_fullName")) {
                osoba.jmenoPrijmeni = value;
            } else if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lbl_pohlavi")) {
                osoba.pohlavi = value;
            } else if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lbl_datumNarozeni")) {
                osoba.datumNarozeni = value;
            } else if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lbl_patraniStart")) {
                osoba.patraniStart = value;
            } else if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lbl_patraniStart")) {
                osoba.bydliste = value;
            } else if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lbl_Dangerous")) {
                osoba.nebezpecny = "ANO".equalsIgnoreCase(value);
            } else if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lbl_Armed")) {
                osoba.ozbrojen = "ANO".equalsIgnoreCase(value);;
            } else if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lbl_Nemoc")) {
                osoba.nakazlivy = "ANO".equalsIgnoreCase(value);;
            } else if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lbl_Nationality")) {
                osoba.statniPrislusnost = value;
            } else if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lbl_Vyska")) {
                osoba.vyska = value;
            } else if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lbl_Age")) {
                osoba.zdanliveStari = value;
            } else if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lblDescription")) {
                osoba.popis = value;
            } else if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lbl_speechLang")) {
                osoba.dalsiUdaje = value;
            }
        }
        return osoba;
    }

    public String getJmenoPrijmeni() {
        return jmenoPrijmeni;
    }

    public String getPohlavi() {
        return pohlavi;
    }

    public String getDatumNarozeni() {
        return datumNarozeni;
    }

    public String getPatraniStart() {
        return patraniStart;
    }

    public String getBydliste() {
        return bydliste;
    }

    public boolean getNebezpecny() {
        return nebezpecny;
    }

    public boolean getOzbrojen() {
        return ozbrojen;
    }

    public boolean getNakazlivy() {
        return nakazlivy;
    }

    public String getStatniPrislusnost() {
        return statniPrislusnost;
    }

    public String getVyska() {
        return vyska;
    }

    public String getZdanliveStari() {
        return zdanliveStari;
    }

    public String getPopis() {
        return popis;
    }

    public String getDalsiUdaje() {
        return dalsiUdaje;
    }

}
