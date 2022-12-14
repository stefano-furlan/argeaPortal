package com.argea.argeaportal.authorization.User;


import java.time.LocalDateTime;

public interface UserLite {

    String getUsername();

    Boolean getEnabled();

    Boolean getBlocked();

    Integer getNumberFailedLogin();

    LocalDateTime getLastLogin();

    String getNome();

    String getCognome();

}
