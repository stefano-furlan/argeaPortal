package com.argea.argeaportal.rest;

import com.argea.argeaportal.database.clienteargea.ClienteArgea;
import com.argea.argeaportal.database.clienteargea.ClienteArgeaRepository;
import com.argea.argeaportal.database.clientecompany.ClienteCompany;
import com.argea.argeaportal.database.clientecompany.ClienteCompanyRepository;
import com.argea.argeaportal.dto.ClienteArgeaDto;
import com.argea.argeaportal.services.ClienteArgeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/rs/md")
public class RestMasterDataController {

    @Autowired
    ClienteArgeaRepository clienteArgeaRepository;

    @Autowired
    ClienteCompanyRepository clienteCompanyRepository;

    @Autowired
    ClienteArgeaService clienteArgeaService;

    @GetMapping("/ping")
    String ping() {
        return "ping RestMasterDataController";
    }


    @PostMapping("/cliente-argea")
    ClienteArgeaDto saveClienteArgea(@RequestBody ClienteArgeaDto clienteArgea) {
        //TODO: aggiungere eventuali controlli di integrità
        return clienteArgeaService.salvaClienteArgea(clienteArgea);
    }

    @GetMapping("/cliente-argea")
    Optional<ClienteArgeaDto> getCliente(@RequestParam(value = "id") Integer idCliente){
        return clienteArgeaService.getClienteArgea(idCliente);
    }

    @PutMapping("/cliente-argea")
    ClienteArgeaDto salvaClienteArgea(@RequestBody  ClienteArgeaDto clienteArgeaDto){
        return clienteArgeaService.salvaClienteArgea(clienteArgeaDto);
    }

    @GetMapping("/clienti-argea")
    Iterable<ClienteArgea> getClienti(){
        return clienteArgeaRepository.findAll();
    }

    @GetMapping("/clienti-company")
    Iterable<ClienteCompany> getClientiGestionali(){
        return clienteCompanyRepository.findAll();
    }



}
