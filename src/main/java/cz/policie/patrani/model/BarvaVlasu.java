package cz.policie.patrani.model;

public enum BarvaVlasu {

    NEVIM("any", "-- jakákoli --"),
    BILE("bílé", "bílé"),
    CERNE("černé", "černé"),
    HNEDE("hnědé", "hnědé"),
    HNEDOCERNE("hnědočerné", "hnědočerné"),
    MELIR("melír", "melír"),
    OBARVENE("obarvené", "obarvené"),
    ODBARVENE("odbarvené", "odbarvené"),
    PLAVE_BLOND("plavé (blond)", "plavé (blond)"),
    RYSAVE("ryšavé", "ryšavé"),
    SEDE("šedé", "šedé")
    ;

    private String value;
    private String caption;

    private BarvaVlasu(String value, String caprion) {
        this.value = value;
        this.caption = caprion;
    }

    public String getValue() {
        return value;
    }

    /**
     * Popis barvy vlasů.
     *
     * @return barva vlasů
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Vrací barvu vlasů odpovídající předanému řetězci.
     *
     * @param caption čitelný text barvy vlasů
     * @return barva vlasů
     */
    public static BarvaVlasu byCaption(String caption) {
        for (BarvaVlasu barvaOci : BarvaVlasu.values()) {
            if (barvaOci.getCaption().equalsIgnoreCase(caption.trim())) {
                return barvaOci;
            }
        }
        throw new IllegalStateException("Nepodporovaná barva vlasů \"" +caption+ "\"");
    }

}
