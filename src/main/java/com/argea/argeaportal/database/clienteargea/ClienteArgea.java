package com.argea.argeaportal.database.clienteargea;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cliente_argea")
public class ClienteArgea implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codice_argea")
    private Integer id;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "intercompany")
    private Boolean intercompany;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Boolean getIntercompany() {
        return intercompany;
    }

    public void setIntercompany(Boolean intercompany) {
        this.intercompany = intercompany;
    }
}
