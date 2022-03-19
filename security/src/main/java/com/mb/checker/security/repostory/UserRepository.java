package com.mb.checker.security.repostory;

import com.mb.checker.security.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, String> {

    AppUser findByUsername(String username);
}
