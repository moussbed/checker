package com.mb.checker.security.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
public class AppUser   {

    @Id
    private String userUuid;

    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> roles= new ArrayList<>();

    public AppUser() {
        this.userUuid= UUID.randomUUID().toString();
    }

    public AppUser(String username, String password, Collection<AppRole> roles) {
        this();
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
