package com.mb.cheker.gateway.config.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.mb.cheker.gateway.execption.JWTTokenExtractException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Component
public class JwtFilter extends AbstractNameValueGatewayFilterFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);

    private static final String WWW_AUTH_HEADER = "WWW-Authenticate";
    private static final String AUTHENTICATION_HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";


    private final JWTVerifier jwtVerifier;

    public JwtFilter(JWTVerifier jwtVerifier) {
        this.jwtVerifier = jwtVerifier;
    }

    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return (exchange, chain) -> {

            try {
                String token = this.extractJWTToken(exchange.getRequest());
                this.jwtVerifier.verify(token);

                ServerHttpRequest request = exchange.getRequest().mutate().
                        header(AUTHENTICATION_HEADER, PREFIX + token).
                        build();

                return chain.filter(exchange.mutate().request(request).build());

            } catch (JWTVerificationException ex) {

                LOGGER.error(ex.toString());
                return this.onError(exchange, ex.getMessage());
            }
        };
    }

    private String extractJWTToken(ServerHttpRequest request) {
        if (!request.getHeaders().containsKey("Authorization")) {
            throw new JWTTokenExtractException("Authorization header is missing");
        }

        List<String> headers = request.getHeaders().get("Authorization");
        if (Objects.requireNonNull(headers).isEmpty()) {
            throw new JWTTokenExtractException("Authorization header is empty");
        }

        String credential = headers.get(0).trim();
        String[] components = credential.split("\\s");

        if (components.length != 2) {
            throw new JWTTokenExtractException("Malformat Authorization content");
        }

        if (!components[0].equals("Bearer")) {
            throw new JWTTokenExtractException("Bearer is needed");
        }

        return components[1].trim();
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(WWW_AUTH_HEADER, err);

        return response.setComplete();
    }

}
