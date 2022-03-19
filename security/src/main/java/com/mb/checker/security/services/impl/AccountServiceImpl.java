package com.mb.checker.security.services.impl;

import com.mb.checker.security.model.AppRole;
import com.mb.checker.security.model.AppUser;
import com.mb.checker.security.repostory.RoleRepository;
import com.mb.checker.security.repostory.UserRepository;
import com.mb.checker.security.services.AccountService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser addUser(AppUser user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public AppRole addRole(AppRole role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRolesToUser(String username, String... roleNames) {
        AppUser user = userRepository.findByUsername(username);

        List<AppRole> roles = Stream.of(roleNames)
                .map(roleRepository::findByRoleName)
                .toList();

        user.getRoles().addAll(roles);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Collection<AppUser> listUsers() {
        return userRepository.findAll();
    }
}
