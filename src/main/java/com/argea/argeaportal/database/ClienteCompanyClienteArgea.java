package com.argea.argeaportal.database;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cliente_company_cliente_argea")
@IdClass(ClienteCompanyClienteArgeaId.class)
public class ClienteCompanyClienteArgea implements Serializable {

    @Id
    @Column(name = "company")
    private String company;

    @Id
    @Column(name = "codice_cliente_company")
    private String codiceClienteCompany;

    @Id
    @Column(name = "codice_cliente_argea")
    private Integer codiceClienteArgea;


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCodiceClienteCompany() {
        return codiceClienteCompany;
    }

    public void setCodiceClienteCompany(String codiceClienteCompany) {
        this.codiceClienteCompany = codiceClienteCompany;
    }

    public Integer getCodiceClienteArgea() {
        return codiceClienteArgea;
    }

    public void setCodiceClienteArgea(Integer codiceClienteArgea) {
        this.codiceClienteArgea = codiceClienteArgea;
    }
}
