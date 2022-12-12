package com.argea.argeaportal.dto;

import com.argea.argeaportal.database.clientecompany.ClienteCompany;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

public class ClienteArgeaDto {


    private String descrizione;

    private Integer id;

    Boolean intercompany;

    List<ClienteCompany> clientiCompany;

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ClienteCompany> getClientiCompany() {
        return clientiCompany;
    }

    public void setClientiCompany(List<ClienteCompany> clientiCompany) {
        this.clientiCompany = clientiCompany;
    }

    public Boolean getIntercompany() {
        return intercompany;
    }

    public void setIntercompany(Boolean intercompany) {
        this.intercompany = intercompany;
    }
}
