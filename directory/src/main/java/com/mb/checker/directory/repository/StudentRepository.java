package com.mb.checker.directory.repository;

import com.mb.checker.directory.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student, String> {
}
