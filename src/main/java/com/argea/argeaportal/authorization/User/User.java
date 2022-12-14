package com.argea.argeaportal.authorization.User;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="users")
public class User implements Serializable {

    @Id
    private String username;
    private String password;
    private Boolean enabled;
    @Column(name = "must_change")
    private Boolean mustChange;
    @Column(name = "user_mapped")
    private String userMapped;
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    @Column(name = "number_failed_login")
    private Integer numberFailedLogin;
    @Column(name = "last_failed_login")
    private LocalDateTime lastFailedLogin;
    @Column(name = "sso_enabled")
    private Boolean ssoEnabled;

    @Column(name = "sso_user")
    private String ssoUser;
    @Column(name = "last_change_password")
    private LocalDateTime lastChangePassword;

    @Column(name = "cognome")
    private String cognome;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "note")
    private String note;

    private Boolean blocked;    //NB: un account può accedere se è enabled=1 e blocked=0

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "authorityId.user",  fetch=FetchType.EAGER, orphanRemoval = true)
    private Set<Authority> authorities = new HashSet<>();



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getMustChange() {
        return mustChange;
    }

    public void setMustChange(Boolean mustChange) {
        this.mustChange = mustChange;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getUserMapped() {
        return userMapped;
    }

    public void setUserMapped(String userMapped) {
        this.userMapped = userMapped;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Integer getNumberFailedLogin() {
        return numberFailedLogin;
    }

    public void setNumberFailedLogin(Integer numberFailedLogin) {
        this.numberFailedLogin = numberFailedLogin;
    }

    public LocalDateTime getLastFailedLogin() {
        return lastFailedLogin;
    }

    public void setLastFailedLogin(LocalDateTime lastFailedLogin) {
        this.lastFailedLogin = lastFailedLogin;
    }

    public Boolean getSsoEnabled() {
        return ssoEnabled;
    }

    public void setSsoEnabled(Boolean ssoEnabled) {
        this.ssoEnabled = ssoEnabled;
    }

    public LocalDateTime getLastChangePassword() {
        return lastChangePassword;
    }

    public void setLastChangePassword(LocalDateTime lastChangePassword) {
        this.lastChangePassword = lastChangePassword;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }


    public String getSsoUser() {
        return ssoUser;
    }

    public void setSsoUser(String ssoUser) {
        this.ssoUser = ssoUser;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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