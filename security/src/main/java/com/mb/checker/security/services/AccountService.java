package com.mb.checker.security.services;


import com.mb.checker.security.model.AppRole;
import com.mb.checker.security.model.AppUser;

import java.util.Collection;

public interface AccountService {

    AppUser addUser(AppUser user);

    AppRole addRole(AppRole role);

    void addRolesToUser(String username, String ... roleNames);

    AppUser loadUserByUsername(String username);

    Collection<AppUser> listUsers();
}


