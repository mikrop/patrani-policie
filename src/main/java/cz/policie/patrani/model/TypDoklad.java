package cz.policie.patrani.model;

public enum TypDoklad {

    OBCANSKY_PRUKAZ("1", "občanský průkaz"),
    FIALOVY_PAS("2", "cestovní pas vydaný centrálně – fialový"),
    ZELENY_PAS("3", "cestovní pas vydávaný regionálně - zelený"),
    ZBROJNI_PRUKAZ("4", "zbrojní průkaz / zbrojní licence")
    ;

    private String value;
    private String caption;

    private TypDoklad(String value, String caprion) {
        this.value = value;
        this.caption = caprion;
    }

    public String getValue() {
        return value;
    }

    /**
     * Popis typu dokladu.
     *
     * @return typ dokladu
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Vrací typ dokladu odpovídající předanému řetězci.
     *
     * @param caption čitelný text typu dokladu
     * @return typ dokladu
     */
    public static TypDoklad byCaption(String caption) {
        for (TypDoklad typDoklad : TypDoklad.values()) {
            if (typDoklad.getCaption().equalsIgnoreCase(caption.trim())) {
                return typDoklad;
            }
        }
        throw new IllegalStateException("Nepodporovaný typ dokladu \"" +caption+ "\"");
    }

}
