package com.argea.argeaportal.authorization.Feature;

public enum FeatureTypeEnum {

    CREAZIONE_CLIENTE_STATISTICO_ARGEA("CREAZIONE_CLIENTE_STATISTICO_ARGEA"),
    LAVORAZIONI_ADMIN("LAVORAZIONI.ADMIN");
    private final String value;

    FeatureTypeEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
