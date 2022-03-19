package com.mb.checker.security.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mb.checker.security.model.AppRole;
import com.mb.checker.security.model.AppUser;
import com.mb.checker.security.model.dto.UsernameRoleNamesDto;
import com.mb.checker.security.services.AccountService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "authentication")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public Collection<AppUser> getUsers() {
        return accountService.listUsers();
    }

    @PostAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public AppUser addUser(@RequestBody @Valid AppUser user) {
        return accountService.addUser(user);
    }

    @PostAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(path = "/roles", method = RequestMethod.POST)
    public AppRole addRole(@RequestBody @Valid AppRole role) {
        return accountService.addRole(role);
    }

    @PostAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(path = "/roles/user", method = RequestMethod.POST)
    public void addRoleToUser(@RequestBody @Valid UsernameRoleNamesDto usernameRoleNamesDto) {
        accountService.addRolesToUser(usernameRoleNamesDto.getUsername(), usernameRoleNamesDto.getRoleNames().toArray(new String[0]));
    }

    @RequestMapping(path = "/refresh-token", method = RequestMethod.GET)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer "))
            throw new RuntimeException("Refresh Required !");

        String refreshToken = authorization.split(" ")[1];

        try {
            Algorithm algorithm = Algorithm.HMAC256("my-secret");

            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);
            String subject = decodedJWT.getSubject();

            AppUser user = accountService.loadUserByUsername(subject);

            String accessToken = JWT.create()
                    .withSubject(subject)
                    .withExpiresAt(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("roles", user.getRoles().stream().map(AppRole::getRoleName).collect(Collectors.toList()))
                    .sign(algorithm);
            HashMap<String, String> idToken = new HashMap<>();

            idToken.put("access-token", accessToken);
            idToken.put("refresh-token", refreshToken);

            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), idToken);


        } catch (Exception e) {
            response.setHeader("error", e.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }

    }

    @PostAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public AppUser getProfile(Principal principal) {
        return accountService.loadUserByUsername(principal.getName());
    }

}
