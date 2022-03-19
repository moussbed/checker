package com.mb.checker.directory.service;

import com.mb.checker.directory.model.Avatar;
import com.mb.checker.directory.model.Student;
import com.mb.checker.shared.common.dto.SchoolingResponse;
import com.mb.checker.shared.common.dto.StudentIdAndFacultyIdDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;


public interface StudentService {

    Student addFacultyToStudent(StudentIdAndFacultyIdDto request);

    Student createStudent(Student student);

    Student updateStudent(String studentUuid, Student student);

    Student getStudent(String studentUuid);

    Collection<Student> getStudents();

    void addAvatarToStudent(String studentUuid, MultipartFile file);

    Avatar getAvatar(String studentUuid);

    SchoolingResponse getSchooling(String studentUuid);

}
