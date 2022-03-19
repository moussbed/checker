package com.mb.checker.clientrest.config.filter;

import com.mb.checker.shared.common.util.JwtUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public RequestInterceptor bearerTokenRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                String accessToken = JwtUtils.getAccessToken();
                template.header("Authorization",
                        String.format("Bearer %s", accessToken));
            }
        };
    }
}
