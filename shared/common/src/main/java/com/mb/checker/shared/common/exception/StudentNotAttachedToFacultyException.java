package com.mb.checker.shared.common.exception;

public class StudentNotAttachedToFacultyException extends RuntimeException{

    public StudentNotAttachedToFacultyException() {
        super("The student is not attached to a faculty");
    }
}
