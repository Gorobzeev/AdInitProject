package com.bcgdv.mobileservice.service.java.com.hjb.managementservice.common;

import lombok.AllArgsConstructor;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

@AllArgsConstructor
public abstract class ContainerInitializer extends TestConfig
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private final PostgreSQLContainer postgres;

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        TestPropertyValues.of(
                "spring.datasource.url=" + postgres.getJdbcUrl(),
                "spring.datasource.username=" + postgres.getUsername(),
                "spring.datasource.password=" + postgres.getPassword()
        ).applyTo(configurableApplicationContext.getEnvironment());
    }

}
