package com.bcgdv.mobileservice.service.java.com.hjb.managementservice.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application.test")
public class TestProperties {

    private String appUserUsername;
    private String appUserPassword;
    private String appAdminUsername;
    private String appAdminPassword;

}
