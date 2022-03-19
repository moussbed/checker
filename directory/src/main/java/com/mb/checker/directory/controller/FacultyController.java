package com.mb.checker.directory.controller;


import com.mb.checker.directory.model.Faculty;
import com.mb.checker.directory.service.FacultyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(path = "faculties")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Faculty> getFaculties(){
        return facultyService.getFaculties();
    }

    @RequestMapping(path = "/{id:.+}",method = RequestMethod.GET)
    public Faculty getFaculty(@PathVariable("id") String facultyUuid){
        return facultyService.getFaculty(facultyUuid);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Faculty createFaculty(@RequestBody @Valid Faculty faculty){
        return facultyService.createFaculty(faculty);
    }
}
