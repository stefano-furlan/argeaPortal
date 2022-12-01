package com.argea.argeaportal.database.clientecompany;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cliente_gestionale")
@IdClass(ClienteCompanyId.class)
public class ClienteCompany implements Serializable {

    @Id
    @Column(name = "company")
    private String company;

    @Id
    @Column(name = "codice_cliente_company")
    private String codiceClienteCompany;

    @Column(name = "descrizione")
    private String descrizione;


    @Column(name = "partita_iva")
    private String partitaIva;


    @Column(name = "codice_fiscale")
    private String codiceFiscale;

    @Column(name = "codice_backoffice")
    private String codiceBackoffice;

    @Column(name = "codice_area_manager")
    private String codiceAreaManager;

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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getCodiceBackoffice() {
        return codiceBackoffice;
    }

    public void setCodiceBackoffice(String codiceBackoffice) {
        this.codiceBackoffice = codiceBackoffice;
    }

    public String getCodiceAreaManager() {
        return codiceAreaManager;
    }

    public void setCodiceAreaManager(String codiceAreaManager) {
        this.codiceAreaManager = codiceAreaManager;
    }
}
