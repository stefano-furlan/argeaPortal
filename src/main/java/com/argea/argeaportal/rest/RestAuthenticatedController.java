package com.argea.argeaportal.rest;

import com.argea.argeaportal.database.ClienteArgea;
import com.argea.argeaportal.database.ClienteArgeaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rs/auth")
public class RestAuthenticatedController {

    @Autowired
    ClienteArgeaRepository clienteArgeaRepository;

    @GetMapping("/ping")
    String ping(){
        return "ping autenticato";
    }






}
