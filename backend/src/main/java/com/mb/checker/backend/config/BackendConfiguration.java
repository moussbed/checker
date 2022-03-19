package com.mb.checker.backend.config;

import com.mb.checker.backend.controller.BackendController;
import com.mb.checker.clientrest.config.ClientRestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ClientRestConfiguration.class,SecurityConfiguration.class})
@ComponentScan(basePackageClasses = BackendController.class)
public class BackendConfiguration {
}
