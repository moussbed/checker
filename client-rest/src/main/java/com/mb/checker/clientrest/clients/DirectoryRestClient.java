package com.mb.checker.clientrest.clients;

import com.mb.checker.clientrest.config.filter.FeignClientConfiguration;
import com.mb.checker.shared.common.dto.SchoolingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "directory-service", configuration = FeignClientConfiguration.class)
public interface DirectoryRestClient {

    @RequestMapping(value = "students/{id:.+}/schooling", method = RequestMethod.GET)
    SchoolingResponse getSchooling(@PathVariable("id") String studentUuid);
}
