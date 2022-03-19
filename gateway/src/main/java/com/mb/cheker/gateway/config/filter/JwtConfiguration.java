package com.mb.cheker.gateway.config.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {


    @Bean
    public JWTVerifier jwtVerifier(@Value("${jwt.secret}") String secret){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm).build();
    }
}
