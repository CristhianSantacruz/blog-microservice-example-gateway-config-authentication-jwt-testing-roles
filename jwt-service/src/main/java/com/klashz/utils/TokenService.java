package com.klashz.utils;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.*;
@ApplicationScoped
public class TokenService {

    //Set<String> roles = new HashSet<>(Arrays.asList("admin","user"));
    public String generateToken(String username,String rol){
       return  Jwt.issuer("jwt-service")
                .subject(username)
                .groups(rol)
                .expiresAt(System.currentTimeMillis()+3600)
                .sign();
    }
}
