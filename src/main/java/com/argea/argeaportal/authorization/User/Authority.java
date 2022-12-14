package com.argea.argeaportal.authorization.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "authorities")
public class Authority {

    @Embeddable
    public static class Pk implements Serializable {
        @ManyToOne
        @JoinColumn(name="username")
        User user;

        @Column(name = "authority")
        String authority;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getAuthority() {
            return authority;
        }

        public void setAuthority(String authority) {
            this.authority = authority;
        }
    }

    @EmbeddedId
    private Pk authorityId;

    public Pk getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Pk authorityId) {
        this.authorityId = authorityId;
    }
}
