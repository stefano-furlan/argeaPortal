package com.argea.argeaportal.rest;

import com.argea.argeaportal.database.ClienteArgea;
import com.argea.argeaportal.database.ClienteArgeaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/rs/md")
public class RestMasterDataController {

    @Autowired
    ClienteArgeaRepository clienteArgeaRepository;

    @GetMapping("/ping")
    String ping() {
        return "ping RestMasterDataController";
    }


    @PostMapping("/cliente-argea")
    ClienteArgea saveClienteArgea(@RequestBody ClienteArgea clienteArgea) {

        //TODO: aggiungere eventuali controlli di integrità
        return clienteArgeaRepository.save(clienteArgea);

    }

    @GetMapping("/cliente-argea")
    Optional<ClienteArgea> getCliente(@RequestParam(value = "idCliente") Integer idCliente){
        return clienteArgeaRepository.findById(idCliente);
    }

    @GetMapping("/clienti-argea")
    Iterable<ClienteArgea> getClienti(){
        return clienteArgeaRepository.findAll();
    }


    //TODO: metodo per esporre a fonte dati esterna (QLIK) la tabella di transcodifica




}
