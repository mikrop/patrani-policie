package cz.policie.patrani;

import java.io.IOException;

/**
 * Vyjímka vyhozená v okamžiku, kdy nebyl objekt v databázi nalezen.
 */
public class PatraniNotFoundException extends IOException {

    /*
                           Doklad s číslem 123456789 nebyl nalezen v databázi neplatných dokladů.
        Mobilní telefon s číslem IMEI 12345678901234 nebyl nalezen v databázi odcizených mobilních telefonů.
                        Na základě zadaných kritérií nebyl nalezen žádný záznam.
     */

    /**
     * Vyjímka informující o nanalezení telefonu s předaným IMEI.
     *
     * @param message text informujici o nenalezení objektu
     */
    public PatraniNotFoundException(String message) {
        super(message);
    }

}
