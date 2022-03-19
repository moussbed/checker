package com.mb.checker.security.repostory;

import com.mb.checker.security.model.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<AppRole, String> {

      AppRole findByRoleName(String roleName);
}
