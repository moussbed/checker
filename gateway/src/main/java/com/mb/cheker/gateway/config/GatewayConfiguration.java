package com.mb.cheker.gateway.config;

import com.mb.cheker.gateway.config.filter.JwtConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({JwtConfiguration.class})
public class GatewayConfiguration {
}
