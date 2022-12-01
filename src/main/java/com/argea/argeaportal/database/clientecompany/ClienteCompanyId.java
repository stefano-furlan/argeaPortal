package com.argea.argeaportal.database.clientecompany;

import java.io.Serializable;
import java.util.Objects;

public class ClienteCompanyId implements Serializable {


    private String company;

    private String codiceClienteCompany;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteCompanyId that = (ClienteCompanyId) o;
        return company.equals(that.company) && codiceClienteCompany.equals(that.codiceClienteCompany);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, codiceClienteCompany);
    }
}
