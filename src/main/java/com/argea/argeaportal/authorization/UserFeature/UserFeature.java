package com.argea.argeaportal.authorization.UserFeature;

import javax.persistence.*;

@Entity
@Table(name = "user_feature")
public class UserFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_feature")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "feature")
    private String feature;

    @Column(name = "enabled")
    private Boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
