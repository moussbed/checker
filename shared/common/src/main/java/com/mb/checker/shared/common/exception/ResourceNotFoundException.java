package com.mb.checker.shared.common.exception;

public class ResourceNotFoundException extends RuntimeException {


    public ResourceNotFoundException(String resource,String id) {
        super(String.format("%s resource with %s not found",resource,id));
    }

}
