/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
/* */
/*
package com.movies4rent.Servidor.Security;

import com.movies4rent.Servidor.Entities.Usuari;
import com.movies4rent.Servidor.Service.UsuariService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author Er_jo
 */
/*
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private UsuariService usuariService;
    

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            Usuari usuari = usuariService.findByUsername(username);
            if (usuari != null) {
                return new org.springframework.security.core.userdetails.User(usuari.getUsername(), usuari.getPassword(), new ArrayList<>());
            } else {
                throw new UsernameNotFoundException ("User not found");
            }
        });
    }
    

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests().requestMatchers("/login").permitAll().anyRequest().authenticated().and().httpBasic();
 
    };
   
}
*/