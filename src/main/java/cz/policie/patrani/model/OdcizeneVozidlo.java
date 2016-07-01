package cz.policie.patrani.model;

import cz.policie.patrani.ScraperUtils;
import cz.policie.patrani.VozidlaPage;
import org.junit.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class OdcizeneVozidlo {

    private String druh;
    private String vyrobce;
    private String typ;
    private String barva;
    private String spz;
    private String mpz;
    private String vin;
    private String motor;
    private Integer rokVyroby;
    private Date nahlaseno;

    private OdcizeneVozidlo() {
    }

    public static OdcizeneVozidlo parse(Map<String, String> map) {
        Assert.assertNotNull(map);

        OdcizeneVozidlo vozidlo = new OdcizeneVozidlo();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String value = entry.getValue().isEmpty() ? null : entry.getValue();
            if (entry.getKey().equals("ctl00_Application_lblDruh")) {
                vozidlo.druh = value;
            } else if (entry.getKey().equals("ctl00_Application_lblVyrobce")) {
                vozidlo.vyrobce = value;
            } else if (entry.getKey().equals("ctl00_Application_lblTyp")) {
                vozidlo.typ = value;
            } else if (entry.getKey().equals("ctl00_Application_lblBarva")) {
                vozidlo.barva = value;
            } else if (entry.getKey().equals("ctl00_Application_lblSPZ")) {
                vozidlo.spz = value;
            } else if (entry.getKey().equals("ctl00_Application_lblMPZ")) {
                vozidlo.mpz = value;
            } else if (entry.getKey().equals("ctl00_Application_lblVIN")) {
                vozidlo.vin = value;
            } else if (entry.getKey().equals("ctl00_Application_lblMotor")) {
                vozidlo.motor = value;
            } else if (entry.getKey().equals("ctl00_Application_lblRokVyroby")) {
                vozidlo.rokVyroby = value != null ? Integer.valueOf(value) : null;
            } else if (entry.getKey().equals("ctl00_Application_lblNahlaseno")) {
                vozidlo.nahlaseno = ScraperUtils.parse(value);
            }
        }
        return vozidlo;
    }

    public String getDruh() {
        return druh;
    }

    public String getVyrobce() {
        return vyrobce;
    }

    public String getTyp() {
        return typ;
    }

    public String getBarva() {
        return barva;
    }

    public String getSpz() {
        return spz;
    }

    public String getMpz() {
        return mpz;
    }

    public String getVin() {
        return vin;
    }

    public String getMotor() {
        return motor;
    }

    public Integer getRokVyroby() {
        return rokVyroby;
    }

    public Date getNahlaseno() {
        return nahlaseno;
    }

}
