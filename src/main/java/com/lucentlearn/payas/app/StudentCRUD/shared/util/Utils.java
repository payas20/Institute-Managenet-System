package com.lucentlearn.payas.app.StudentCRUD.shared.util;

import com.lucentlearn.payas.app.StudentCRUD.service.impl.JwtServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Utils {

    @Autowired
    JwtServiceImpl jwtService;

    public String getUserName(HttpServletRequest request){
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);

        return username;
    }
}
