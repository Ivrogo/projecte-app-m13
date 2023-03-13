package com.movies4rent.Servidor.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.movies4rent.Servidor.Entities.Usuari;
import com.movies4rent.Servidor.Service.UsuariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class Helpers {

    @Autowired
    private UsuariService service;


    public String createToken(Usuari usuari, boolean rememberMe) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode roles = mapper.createArrayNode();
        System.out.println("Create" + usuari.getUsername());
        ResponseEntity<List<Usuari>> foundUser = service.findByUsername("username=" + usuari.getUsername() + "'");
        System.out.println(foundUser.getBody().size());
        for (Usuari usuari1 : foundUser.getBody()) {
            roles.add(usuari1.isAdmin());
        }
        ObjectNode detail = mapper.createObjectNode();
        detail.set("usuariRoles", roles);
        if (rememberMe) {
            detail.put("expireTime", LocalDateTime.now().plusMinutes(7 * 24 * 60 * 60).toString());
        } else {
            detail.put("expireTime", LocalDateTime.now().plusMinutes(2 * 60 * 60).toString());
        }
        detail.put("userName", usuari.getUsername());
        detail.put("usuariId", usuari.getId());
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(detail);
        String token = EncoderUtil.getAESEncrypt(json);
        return token;
    }

}
