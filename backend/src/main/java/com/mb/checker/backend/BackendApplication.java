package com.mb.checker.backend;

import com.mb.checker.backend.config.BackendConfiguration;
import com.mb.checker.clientrest.clients.DirectoryRestClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableFeignClients(basePackageClasses = {DirectoryRestClient.class})
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import({BackendConfiguration.class})
@SpringBootApplication
public class BackendApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
