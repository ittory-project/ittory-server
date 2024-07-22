package com.ittory.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.ittory.infra", "com.ittory.common"})
public class AppConfig {
}
