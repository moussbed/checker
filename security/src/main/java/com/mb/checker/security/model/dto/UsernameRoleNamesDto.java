package com.mb.checker.security.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class UsernameRoleNamesDto {

    private String username;

    private List<String> roleNames;
}
