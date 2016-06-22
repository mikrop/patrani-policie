package cz.policie.patrani.model;

public enum BarvaOci {

    NEVIM("any", "-- jakákoli --"),
    CERNA("černá", "černá"),
    HNEDA("hnědá", "hnědá"),
    MODRA("modrá", "modrá"),
    ZELENA("zelená", "zelená")
    ;

    private String value;
    private String caption;

    private BarvaOci(String value, String caprion) {
        this.value = value;
        this.caption = caprion;
    }

    public String getValue() {
        return value;
    }

    /**
     * Čitelný text barvy očí.
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Vrací barvu očí odpovídající předanému řetězci.
     *
     * @param caption čitelný text barvy očí
     * @return barva očí
     */
    public static BarvaOci byCaption(String caption) {
        for (BarvaOci barvaOci : BarvaOci.values()) {
            if (barvaOci.getCaption().equalsIgnoreCase(caption.trim())) {
                return barvaOci;
            }
        }
        throw new IllegalStateException("Nepodporovaná barva očí \"" +caption+ "\"");
    }

}
