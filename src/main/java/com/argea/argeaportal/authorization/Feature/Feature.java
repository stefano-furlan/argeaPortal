package com.argea.argeaportal.authorization.Feature;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_feature")
public class Feature {

    @Id
    @Column(name = "feature")
    private String id;

    @Column(name = "note")
    private String note;

    @Column(name = "gruppo")
    private String gruppo;

    @Column(name = "required_authorities")
    private String requiredAuthorities;

    @Column(name = "hidden")
    private Boolean hidden;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getGruppo() {
        return gruppo;
    }

    public void setGruppo(String gruppo) {
        this.gruppo = gruppo;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public String getRequiredAuthorities() {
        return requiredAuthorities;
    }

    public void setRequiredAuthorities(String requiredAuthorities) {
        this.requiredAuthorities = requiredAuthorities;
    }
}
