package cz.policie.patrani.model;

import java.util.regex.Pattern;

public enum Pohlavi {

    MUZ("MUŽ", "muž"),
    ZENA("ŽENA", "žena")
    ;

    private String value;
    private String caption;

    private Pohlavi(String value, String caprion) {
        this.value = value;
        this.caption = caprion;
    }

    public String getValue() {
        return value;
    }

    /**
     * Čitelný text pohlaví.
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Vrací pohlaví odpovídající předanému řetězci.
     *
     * @param caption čitelný text pohlaví
     * @return pohlaví
     */
    public static Pohlavi byCaption(String caption) {
        for (Pohlavi pohlavi : Pohlavi.values()) {
            if (pohlavi.getCaption().equalsIgnoreCase(caption.trim())) {
                return pohlavi;
            }
        }
        throw new IllegalStateException("Nepodporovaný typ pohlaví \"" +caption+ "\"");
    }

}
