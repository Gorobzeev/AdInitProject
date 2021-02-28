package com.bcgdv.mobileservice;

import com.bcgdv.mobileservice.encription.EnableEncryptInjection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptInjection(packageForScan = "com.bcgdv.mobileservice.model")
public class MobileServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MobileServiceApplication.class, args);
    }
}
