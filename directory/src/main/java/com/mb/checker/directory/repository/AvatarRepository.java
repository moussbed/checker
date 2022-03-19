package com.mb.checker.directory.repository;

import com.mb.checker.directory.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AvatarRepository extends JpaRepository<Avatar, String> {
    Optional<Avatar> findByName(String name);
}
