package cz.policie.patrani.model;

public enum HledanyPohresovany {

    NEVIM("any", "-- nevím --"),
    HLEDANY("1", "hledaný"),
    POHRESOVANY("0", "pohřešovaný")
    ;

    private String value;
    private String caption;

    private HledanyPohresovany(String value, String caprion) {
        this.value = value;
        this.caption = caprion;
    }

    public String getValue() {
        return value;
    }

    /**
     * Popis hledaný nebo pohřešovaný.
     *
     * @return hledaný nebo pohřešovaný
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Vrací výčet hledaný nebo pohřešovaný odpovídající předanému řetězci.
     *
     * @param caption čitelný text pohlaví
     * @return hledaný nebo pohřešovaný
     */
    public static HledanyPohresovany byCaption(String caption) {
        for (HledanyPohresovany hledanyPohresovany : HledanyPohresovany.values()) {
            if (hledanyPohresovany.getCaption().equalsIgnoreCase(caption.trim())) {
                return hledanyPohresovany;
            }
        }
        throw new IllegalStateException("Nepodporovaný typ hledaný nebo pohřešovaný \"" +caption+ "\"");
    }

}
