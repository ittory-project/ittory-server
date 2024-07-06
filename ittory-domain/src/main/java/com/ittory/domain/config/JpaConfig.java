package com.ittory.domain.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {"com.ittory.domain"})
@EntityScan(basePackages = {"com.ittory.domain"})
@EnableJpaRepositories(basePackages = {"com.ittory.domain"})
public class JpaConfig {
}
