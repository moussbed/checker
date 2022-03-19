package com.mb.checker.clientrest.config;

import com.mb.checker.clientrest.clients.DirectoryRestClient;
import com.mb.checker.clientrest.config.filter.FeignClientConfiguration;
import com.mb.checker.clientrest.errors.CommonErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({FeignClientConfiguration.class})
@ComponentScan(basePackageClasses = {DirectoryRestClient.class})
public class ClientRestConfiguration {

    @Bean
    public CommonErrorDecoder commonErrorDecoder(){
         return new CommonErrorDecoder();
    }
}
