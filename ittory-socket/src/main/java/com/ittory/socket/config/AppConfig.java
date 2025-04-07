package com.ittory.socket.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.ittory.common", "com.ittory.infra"})
public class AppConfig {
}
