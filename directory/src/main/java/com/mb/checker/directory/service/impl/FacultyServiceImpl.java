package com.mb.checker.directory.service.impl;

import com.mb.checker.directory.model.Faculty;
import com.mb.checker.directory.repository.FacultyRepository;
import com.mb.checker.directory.service.FacultyService;
import com.mb.checker.shared.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFaculty(String facultyUuid) {
        return facultyRepository.findById(facultyUuid).orElseThrow(()-> new ResourceNotFoundException(Faculty.class.getSimpleName(),facultyUuid));
    }

    @Override
    public Collection<Faculty> getFaculties() {
        return facultyRepository.findAll();
    }
}
