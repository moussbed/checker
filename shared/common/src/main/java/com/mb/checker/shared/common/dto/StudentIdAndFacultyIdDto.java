package com.mb.checker.shared.common.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class StudentIdAndFacultyIdDto {

    @NotNull
    private String facultyUuid;

    private String studentUuid;
}
