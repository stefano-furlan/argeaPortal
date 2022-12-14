package com.argea.argeaportal.authorization.User;

import com.argea.argeaportal.authorization.UserFeature.UserFeature;

import java.util.List;

public class UserInfo {

    private String username;
    private String nome;
    private String cognome;
    private String codiceFornitore;
    private String preferredLanguage;
    private List<UserFeature> userFeatures;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<UserFeature> getUserFeatures() {
        return userFeatures;
    }

    public void setUserFeatures(List<UserFeature> userFeatures) {
        this.userFeatures = userFeatures;
    }

    public String getCodiceFornitore() {
        return codiceFornitore;
    }

    public void setCodiceFornitore(String codiceFornitore) {
        this.codiceFornitore = codiceFornitore;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }
}
