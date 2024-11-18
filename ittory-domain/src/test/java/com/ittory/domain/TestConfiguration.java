package com.ittory.domain;

import com.ittory.domain.config.QueryDslConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({QueryDslConfig.class})
public class TestConfiguration {
}
