package com.argea.argeaportal.rest.errorHandling;

/*
    modella errori potenzialmente dipendenti da utente
 */
public class PortalOperationNotPermittedException extends RuntimeException {

    private final String type="OPERATION_NOT_PERMITTED";
    private String code;

    public PortalOperationNotPermittedException(String message) {
        super(message);
    }

    public PortalOperationNotPermittedException(String message, String code) {
        super(message);
        this.code = code;
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
