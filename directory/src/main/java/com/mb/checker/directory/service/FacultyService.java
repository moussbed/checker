package com.mb.checker.directory.service;

import com.mb.checker.directory.model.Faculty;

import java.util.Collection;

public interface FacultyService {

    Faculty createFaculty(Faculty faculty);

    Faculty getFaculty(String facultyUuid);

    Collection<Faculty> getFaculties();
}
