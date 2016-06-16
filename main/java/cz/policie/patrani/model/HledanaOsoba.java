package cz.policie.patrani.model;

import cz.policie.patrani.ScraperUtils;
import org.junit.Assert;

import java.awt.font.NumericShaper;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ValueRange;
import java.util.Map;
import java.util.stream.IntStream;

public class HledanaOsoba {

    private Pohlavi pohlavi = Pohlavi.MUZ;
    private HledanyPohresovany hledanyPohresovany = HledanyPohresovany.NEVIM;
    private String jmenoPrijmeni = "";
    private String bydliste = "any";
    private String obcanstvi = "any";
    private String vekOd = "";
    private String vekDo = "";
    private String vyskaOd = "";
    private String vyskaDo = "";
    private BarvaOci barvaOci = BarvaOci.NEVIM;
    private BarvaVlasu barvaVlasu = BarvaVlasu.NEVIM;

    private String datumNarozeni;
    private boolean nebezpecny;
    private boolean ozbrojeny;
    private boolean nemocny;
    private boolean nakazlivy;
    private String narodnost;
    private String popis;
    private String dalsiUdaje;

    public static HledanaOsoba parse(Map<String, String> map) {
        Assert.assertNotNull(map);

        HledanaOsoba osoba = new HledanaOsoba();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String value = entry.getValue().isEmpty() ? null : entry.getValue();
            if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lbl_fullName")) {
                osoba.jmenoPrijmeni = value;
            } else if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lbl_pohlavi")) {
                osoba.pohlavi = Pohlavi.byLblPohlavi(value);
            } else if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lbl_datumNarozeni")) {
                osoba.datumNarozeni = value;
            } else if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lbl_Dangerous")) {
                osoba.nebezpecny = ScraperUtils.toBoolean(value);
            } else if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lbl_Armed")) {
                osoba.ozbrojeny = ScraperUtils.toBoolean(value);;
            } else if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lbl_Nemoc")) {
                osoba.nakazlivy = ScraperUtils.toBoolean(value);;
            } else if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lbl_Nationality")) {
                osoba.narodnost = value;
            } /*else if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lbl_Vyska")) {
                osoba.vyska = value;
            } else if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lbl_Age")) {
                osoba.vek = value;
            }*/ else if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lblDescription")) {
                osoba.popis = value;
            } else if (entry.getKey().equals("ctl00_ctl00_Application_BasePlaceHolder_lbl_speechLang")) {
                osoba.dalsiUdaje = value;
            }
        }
        return osoba;
    }

    public Pohlavi getPohlavi() {
        return pohlavi;
    }

    public void setPohlavi(Pohlavi pohlavi) {
        this.pohlavi = pohlavi;
    }

    public HledanyPohresovany getHledanyPohresovany() {
        return hledanyPohresovany;
    }

    public void setHledanyPohresovany(HledanyPohresovany hledanyPohresovany) {
        this.hledanyPohresovany = hledanyPohresovany;
    }

    public String getJmenoPrijmeni() {
        return jmenoPrijmeni;
    }

    public void setJmenoPrijmeni(String jmenoPrijmeni) {
        this.jmenoPrijmeni = jmenoPrijmeni;
    }

    public String getBydliste() {
        return bydliste;
    }

    public void setBydliste(String bydliste) {
        this.bydliste = bydliste;
    }

    public String getObcanstvi() {
        return obcanstvi;
    }

    public void setObcanstvi(String obcanstvi) {
        this.obcanstvi = obcanstvi;
    }

    public String getVekOd() {
        return vekOd;
    }

    public void setVekOd(String vekOd) {
        this.vekOd = vekOd;
    }

    public String getVekDo() {
        return vekDo;
    }

    public void setVekDo(String vekDo) {
        this.vekDo = vekDo;
    }

    public String getVyskaOd() {
        return vyskaOd;
    }

    public void setVyskaOd(String vyskaOd) {
        this.vyskaOd = vyskaOd;
    }

    public String getVyskaDo() {
        return vyskaDo;
    }

    public void setVyskaDo(String vyskaDo) {
        this.vyskaDo = vyskaDo;
    }

    public BarvaOci getBarvaOci() {
        return barvaOci;
    }

    public void setBarvaOci(BarvaOci barvaOci) {
        this.barvaOci = barvaOci;
    }

    public BarvaVlasu getBarvaVlasu() {
        return barvaVlasu;
    }

    public void setBarvaVlasu(BarvaVlasu barvaVlasu) {
        this.barvaVlasu = barvaVlasu;
    }

    public String getDatumNarozeni() {
        return datumNarozeni;
    }

    public boolean isNebezpecny() {
        return nebezpecny;
    }

    public boolean isOzbrojeny() {
        return ozbrojeny;
    }

    public boolean isNemocny() {
        return nemocny;
    }

    public boolean isNakazlivy() {
        return nakazlivy;
    }

    public String getNarodnost() {
        return narodnost;
    }

    public String getPopis() {
        return popis;
    }

    public String getDalsiUdaje() {
        return dalsiUdaje;
    }

}
