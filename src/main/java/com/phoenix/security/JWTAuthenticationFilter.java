package com.phoenix.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenix.data.models.AppUser;
import org.apache.catalina.mapper.Mapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private ObjectMapper mapper;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.mapper = new ObjectMapper();
    }


    @Override
    public Authentication attemptAuthentication
            (HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            //read credentials from http request
            AppUser appUser = mapper.readValue(request.getInputStream(), AppUser.class);
            //create username password token
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(appUser.getEmail(), appUser.getPassword());

            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {


        String jwtToken = JWT.create().withSubject(((User)
                authResult.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + "864_000_000"))
                .sign(Algorithm.HMAC256("MyAppSecret@!".getBytes()));


        //return response
        response.addHeader("Authorization", jwtToken);
    }

}