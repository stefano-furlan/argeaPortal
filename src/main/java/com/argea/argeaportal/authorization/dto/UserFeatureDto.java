package com.argea.argeaportal.authorization.dto;


public class UserFeatureDto {

    private String username;
    private String feature;
    private Boolean value;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
}
