package com.argea.argeaportal.rest.errorHandling;

/*
 * modella le eccezzioni per codice non implementato o errori logici indipendenti da utente
 */
public class PortalIntersystemException extends RuntimeException {

    private final String type="INTERSYSTEM";
    private String code;
    private PortalErrorUserCategory category;

    public PortalIntersystemException(String message) {
        super(message);
    }

    public PortalIntersystemException(String message, String code) {
        super(message);
        this.code = code;
    }
    public PortalIntersystemException(String message, String code, PortalErrorUserCategory category) {
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
