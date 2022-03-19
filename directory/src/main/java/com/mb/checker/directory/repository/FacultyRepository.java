package com.mb.checker.directory.repository;

import com.mb.checker.directory.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FacultyRepository extends JpaRepository<Faculty, String> {
}
