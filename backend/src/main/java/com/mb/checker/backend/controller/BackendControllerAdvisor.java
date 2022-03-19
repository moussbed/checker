package com.mb.checker.backend.controller;

import com.mb.checker.clientrest.exceptions.NotAuthorizeClientException;
import com.mb.checker.clientrest.exceptions.ResourceNotFoundClientException;
import com.mb.checker.shared.common.exception.StudentNotAttachedToFacultyException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class BackendControllerAdvisor  {


    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Object> handleHttpClientErrorException(
            HttpClientErrorException exception,WebRequest request){

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", exception.getStatusText());
        return new ResponseEntity<>(body, exception.getStatusCode());
    }
}
