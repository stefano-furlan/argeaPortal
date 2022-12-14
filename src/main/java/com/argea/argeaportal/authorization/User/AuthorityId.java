package com.argea.argeaportal.authorization.User;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AuthorityId implements Serializable {
    private String username;
    private String authority;

    // default constructor
    public AuthorityId(){

    }
    public AuthorityId(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }


}
