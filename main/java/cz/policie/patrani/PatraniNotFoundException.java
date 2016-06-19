package cz.policie.patrani;

import org.jsoup.select.Elements;

/**
 * Vyjímka vyhozená v okamžiku, kdy nebyl objekt v databázi nalezen.
 */
public class PatraniNotFoundException extends RuntimeException {

    /*
                          Doklad s číslem \w nebyl nalezen v databázi neplatných dokladů.
        Mobilní telefon s číslem IMEI \d{14} nebyl nalezen v databázi odcizených mobilních telefonů.
                                                 Nenalezen žádný záznam
                Na základě zadaných kritérií nebyl nalezen žádný záznam.
     */

    /**
     * Vyjímka informující o nanalezení objektu.
     *
     * @param elements element obsahující text o nenalezení objektu
     */
    public PatraniNotFoundException(Elements elements) {
        super(elements.text());
    }

}
