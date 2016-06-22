package cz.policie.patrani.model;

/**
 * Osoba reprezentující vstupní formulář pátrání po osobách.
 */
public class Osoba {

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

}
