package com.mb.checker.security.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
public class AppRole {

    @Id
    private String roleUuid;

    private String roleName;

    public AppRole() {
        this.roleUuid = UUID.randomUUID().toString();
    }

    public AppRole(String roleName) {
        this();
        this.roleName = roleName;
    }
}
