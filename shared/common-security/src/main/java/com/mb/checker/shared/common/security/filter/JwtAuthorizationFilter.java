package com.mb.checker.shared.common.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mb.checker.shared.common.util.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final String secret;

    public JwtAuthorizationFilter(String secret) {
        this.secret=secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String authorization = request.getHeader("Authorization");
        List<String> paths = List.of("/authentication/refresh-token", "/login");

        final String servletPath = request.getServletPath();
        if(paths.contains(servletPath)){
            filterChain.doFilter(request, response);
        }else {

            if (authorization == null || !authorization.startsWith("Bearer "))
                throw new JWTVerificationException("Authorization header is missing");

            String token = authorization.split(" ")[1];

            try {
                Algorithm algorithm = Algorithm.HMAC256(secret);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(token);
                String subject = decodedJWT.getSubject();
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                subject,
                                null,
                                Stream.of(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

                JwtUtils.setAccessToken(token);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);

            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }

        }
    }
}

