package com.argea.argeaportal.database;

import java.io.Serializable;
import java.util.Objects;

public class ClienteCompanyClienteArgeaId implements Serializable {


    private String company;

    private String codiceClienteCompany;

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
