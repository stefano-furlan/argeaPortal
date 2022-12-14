package com.argea.argeaportal.rest.errorHandling;

public enum PortalErrorUserCategory {
    APPLICATION_ERROR("APPLICATION_ERROR")
    ,OPERATION_NOT_ALLOWED("OPERATION_NOT_ALLOWED")
    ,BLOCKING_WARNING("BLOCKING_WARNING")
    ,WARNING("WARNING");
    //,CREARE_SCHEDA("CREARE_SCHEDA");
    private final String value;

    PortalErrorUserCategory(String value) {
        this.value = value;
    }
    public String value() {
        return this.value;
    }
}
