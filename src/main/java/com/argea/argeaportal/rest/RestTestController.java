package com.argea.argeaportal.rest;

import com.argea.argeaportal.database.ClienteArgea;
import com.argea.argeaportal.database.ClienteArgeaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


/**
 * endpoint non autenticato
 */
@RestController
@RequestMapping("/rs/test")
public class RestTestController {

    @Autowired
    ClienteArgeaRepository clienteArgeaRepository;

    /**
     * metodo di test che ritorna una stringa(NB: i metodi rest non dovrebbero MAI ritornare stringhe, solo json)
     * usato per testare con postman che la api sia correttamente configurata
     * @return
     */
    @GetMapping("/ping")
    String ping(){
        return "ciao";
    }


    /**
     * REST API che (sbagliando :) a fronte di una GET effettua la scrittura su db
     * utile per testare
     * @return
     */
    @GetMapping("/scrivicliente")
    String testScrittura(){
        ClienteArgea clienteArgea=new ClienteArgea();
        clienteArgea.setDescrizione("pippo");

        clienteArgeaRepository.save(clienteArgea);
        return "fatto";
    }

    @GetMapping("/clienti")
    Iterable<ClienteArgea> getClienti(){
       return clienteArgeaRepository.findAll();
    }

    @GetMapping("/cliente")
    Optional<ClienteArgea> getCliente(@RequestParam(value = "idCliente") Integer idCliente){
        return clienteArgeaRepository.findById(idCliente);
    }



}
