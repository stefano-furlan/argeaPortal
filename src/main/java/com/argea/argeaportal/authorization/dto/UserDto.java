package com.argea.argeaportal.authorization.dto;


public class UserDto {

    private String username;

    private Boolean enabled;
    private Boolean mustChangePassword;
    private Boolean blocked;
    private Boolean ssoEnabled;
    private String ssoUser;

    private String nome;
    private String cognome;
    private String email;

    private String note;



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getMustChangePassword() {
        return mustChangePassword;
    }

    public void setMustChangePassword(Boolean mustChangePassword) {
        this.mustChangePassword = mustChangePassword;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Boolean getSsoEnabled() {
        return ssoEnabled;
    }

    public void setSsoEnabled(Boolean ssoEnabled) {
        this.ssoEnabled = ssoEnabled;
    }

    public String getSsoUser() {
        return ssoUser;
    }

    public void setSsoUser(String ssoUser) {
        this.ssoUser = ssoUser;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
