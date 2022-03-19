package com.mb.checker.backend.controller;

import com.mb.checker.clientrest.clients.DirectoryRestClient;
import com.mb.checker.shared.common.dto.SchoolingResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "backend/public-api")
public class BackendController {


    private final DirectoryRestClient directoryRestClient;

    public BackendController(DirectoryRestClient directoryRestClient) {
        this.directoryRestClient = directoryRestClient;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CHECK-QRCODE')")
    @RequestMapping(value = "/students/{id:.+}/schooling", method = RequestMethod.GET)
    SchoolingResponse getSchooling(@PathVariable("id") String studentUuid) {
        return directoryRestClient.getSchooling(studentUuid);
    }
}
