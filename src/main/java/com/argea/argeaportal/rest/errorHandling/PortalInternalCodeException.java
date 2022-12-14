package com.argea.argeaportal.rest.errorHandling;

/*
 * modella le eccezzioni per codice non implementato o errori logici indipendenti da utente
 */
public class PortalInternalCodeException extends RuntimeException {

    private final String type="INTERNALCODE";
    private String code;
    private PortalErrorUserCategory category;

    public PortalInternalCodeException(String message) {
        super(message);
    }

    public PortalInternalCodeException(String message, String code) {
        super(message);
        this.code = code;
    }
    public PortalInternalCodeException(String message, String code, PortalErrorUserCategory category) {
        super(message);
        this.code = code;
        this.category=category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }
}
