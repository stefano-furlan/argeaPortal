package com.argea.argeaportal.database;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClienteCompanyClienteArgeaRepository extends CrudRepository<ClienteCompanyClienteArgea, ClienteCompanyClienteArgeaId> {

    List<ClienteCompanyClienteArgea> findAllByCodiceClienteArgea(Integer codiceClienteArgea);

}
