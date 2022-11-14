package com.argea.argeaportal.rest;

import com.argea.argeaportal.database.ClienteArgea;
import com.argea.argeaportal.database.ClienteArgeaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rs/md")
public class RestMasterDataController {

    @Autowired
    ClienteArgeaRepository clienteArgeaRepository;

    @GetMapping("/ping")
    String ping() {
        return "ping RestMasterDataController";
    }


    @PostMapping("/cliente-argea/save")
    ClienteArgea saveClienteArgea(@RequestBody ClienteArgea clienteArgea) {

        //TODO: aggiungere eventuali controlli di integrità
        return clienteArgeaRepository.save(clienteArgea);

    }


    //TODO: metodo per esporre a fonte dati esterna (QLIK) la tabella di transcodifica




}
