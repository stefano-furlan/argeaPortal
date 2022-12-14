package com.argea.argeaportal.rest.errorHandling;

/*
    modella errori di sicurezza.
    dovrebbe inviare email a supervisor e mascherare il vero errore prima di propagarlo
 */
public class PortalSecurityException extends RuntimeException {



    private final String type="UNAUTHORIZED";
    private final String DEFAULTMESSAGE="UNAUTHORIZED";
    private String code;
    private PortalErrorUserCategory category;

    public PortalSecurityException(String message) {
        super("UNAUTHORIZED");
        //SecurityNotificationService.notify(message);
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
