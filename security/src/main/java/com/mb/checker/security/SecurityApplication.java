package com.mb.checker.security;

import com.mb.checker.security.config.SecurityConfiguration;
import com.mb.checker.security.config.flyway.FlywayMigrationConfiguration;
import com.mb.checker.security.controller.AccountController;
import com.mb.checker.security.repostory.RoleRepository;
import com.mb.checker.security.services.AccountService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import({SecurityConfiguration.class, FlywayMigrationConfiguration.class})
@ComponentScan(basePackageClasses = {AccountService.class, RoleRepository.class, AccountController.class})
public class SecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }
}
