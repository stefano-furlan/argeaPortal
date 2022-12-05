package com.argea.argeaportal.rest.errorHandling;

public class PortalErrorResponse {
    private String message;
    private String code;
    private String type;

    public PortalErrorResponse(String message) {
        this.message = message;
    }

    public PortalErrorResponse(String message, String code, String type) {
        this.message = message;
        this.code = code;
        this.type=type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public void setType(String type) {
        this.type = type;
    }
}
