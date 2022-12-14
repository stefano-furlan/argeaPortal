package com.argea.argeaportal.rest.errorHandling;

/*
    modella errori potenzialmente dipendenti da utente
 */
public class PortalResourceNotFoundException extends RuntimeException {

    private final String type="RECOVERABLE";
    private String code;
    private PortalErrorUserCategory category;

    public PortalResourceNotFoundException(String message) {
        super(message);
    }

    public PortalResourceNotFoundException(String message, String code) {
        super(message);
        this.code = code;
    }
    public PortalResourceNotFoundException(String message, String code, PortalErrorUserCategory category) {
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
