package com.argea.argeaportal.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {


    @Autowired
    private DataSource dataSource;

   /*
   * security triviale
   * *//***
     * QUI CONFIGURIAMO GLI UTENTI(al momento metto un utente statico, poi mettiamo gli utenti nel db)
     * @return
     *//*
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        var userDetailsService =
                new InMemoryUserDetailsManager();
        var user = User.withUsername("user")
                .password("password")
                .authorities("USER")
                .build();
        userDetailsService.createUser(user);
        return userDetailsService;
    }

    *//***
     * qui a regime useremo un encoder corretto(con salatura della pass ecc)
     * questo è solo per vedere
     * @return
     *//*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }*/


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.httpBasic();  //usa basic authentication


        //qui di seguito metto la sicurezza sui vari endpoint (non è l'unico modo, ce n'è un altro più carino, ma ti spiego poi)
        http.authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers("/rs/test/**").permitAll() //qui tutti possono entrare
                                .antMatchers("/rs/md/**").permitAll()//TODO: abilitare role based security
                                .antMatchers("/rs/auth/**").authenticated()   //qui devo mettere la password
                                .anyRequest().authenticated()
                );

        return http.build();
    }


    //con postman prova a chiamare
    //http://localhost:8080/rs/test/ping
    //http://localhost:8080/rs/auth/ping   (qui devi usare basicauth con login=user e pass=password)
    //http://localhost:8080/rs/test/clienti (qui ti esce un bel json con i clienti nella tabella clienti_argea)


}
