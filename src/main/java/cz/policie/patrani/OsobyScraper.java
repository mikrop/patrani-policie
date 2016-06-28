package cz.policie.patrani;

import cz.policie.patrani.model.Osoba;

import java.io.IOException;

/**
 * Parsuje webové stránky policie české republiky <a href="http://aplikace.policie.cz/patrani-osoby/Vyhledavani.aspx">pátrání po osobách</a>
 */
public class OsobyScraper {

    /**
     * Seznam hledaných osob odpovídajících předanému formuláři.
     *
     * @param osoba filtry hledané osoby
     * @return seznam hledaných osob odpovídajících předanému filtru
     * @throws IOException chyba parsování
     */
    public static OsobyPage parse(Osoba osoba) throws IOException {
        return new OsobyPage(osoba);
    }

}
