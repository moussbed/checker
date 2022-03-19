package com.mb.checker.directory.controller;

import com.mb.checker.directory.model.Avatar;
import com.mb.checker.directory.model.Student;
import com.mb.checker.directory.service.StudentService;
import com.mb.checker.shared.common.dto.SchoolingResponse;
import com.mb.checker.shared.common.dto.StudentIdAndFacultyIdDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(path = "students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Student> getStudents(){
        return studentService.getStudents();
    }

    @RequestMapping(path = "/{id:.+}",method = RequestMethod.GET)
    public Student getStudent(@PathVariable("id") String studentUuid){
        return studentService.getStudent(studentUuid);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@RequestBody @Valid Student student){
       return studentService.createStudent(student);
    }

    @RequestMapping(path = "/{id:.+}",method = RequestMethod.PUT)
    public Student updateStudent(@PathVariable("id") String studentUuid,@RequestBody @Valid Student student){
        return studentService.updateStudent(studentUuid,student);
    }


    @RequestMapping(path ="/{id:.+}/faculty" ,method = RequestMethod.POST )
    public Student addFacultyToStudent(@PathVariable("id")String studentUuid, @RequestBody @Valid StudentIdAndFacultyIdDto request){
        request.setStudentUuid(studentUuid);
        return studentService.addFacultyToStudent(request);
    }

    @RequestMapping(value = "/{id:.+}/avatar", method = RequestMethod.POST)
    public void addAvatarToStudent(@PathVariable("id")String studentUuid, @RequestParam("imageFile") MultipartFile file){
        studentService.addAvatarToStudent(studentUuid,file);
    }

    @RequestMapping(value = "/{id:.+}/avatar", method = RequestMethod.GET)
    public Avatar getAvatar(@PathVariable("id")String studentUuid){
       return studentService.getAvatar(studentUuid);
    }

    @RequestMapping(value = "/{id:.+}/schooling", method = RequestMethod.GET)
    public SchoolingResponse getSchooling(@PathVariable("id")String studentUuid){
        return studentService.getSchooling(studentUuid);
    }
}
