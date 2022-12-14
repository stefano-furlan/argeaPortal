package com.argea.argeaportal.authorization.dto;

public class UserDtoCreation extends UserDto{

    private String newPassword;


    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
