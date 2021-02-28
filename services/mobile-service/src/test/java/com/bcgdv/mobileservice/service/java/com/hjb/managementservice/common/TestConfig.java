package com.bcgdv.mobileservice.service.java.com.hjb.managementservice.common;


import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.Collection;

@Configuration
public class TestConfig {

//    private static final String userImagesUploadDir;
//    private static final String criterionIconUploadDir;
//    private static final String subCriterionIconUploadDir;

//    static {
//        File parentDirectory = new File("./src/test/resources/files");
//        userImagesUploadDir = parentDirectory.getAbsolutePath() + "/users/images";
//        criterionIconUploadDir = parentDirectory.getAbsolutePath() + "/criteria/icons";
//        subCriterionIconUploadDir = parentDirectory.getAbsolutePath() + "/sub_criteria/icons";
//    }

//    @Bean
//    public FileStorageRepository fileStorageRepository() {
//        return new FileStorageRepositoryImpl(
//                userImagesUploadDir,
//                criterionIconUploadDir,
//                subCriterionIconUploadDir
//        );
//    }

//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

//    @Bean
//    @Primary
//    public InMemoryTokenStore tokenStore() {
//        return new InMemoryTokenStore();
//    }
//
//    @Bean
//    public FeignLocationService feignLocationService() {
//        return new FeignLocationService() {
//            @Override
//            public Collection<ZipCodeDTO> findAllStartingFrom(String start) {
//                return null;
//            }
//        };
//    }

//    @Bean
//    public FeignUserService feignUserService() {
//        return new FeignUserService() {
//            @Override
//            public ResponseEntity<Void> getAndSendEmail(AdvertisementDeactivationEmail deactivationEmail) {
//                return null;
//            }
//
//            @Override
//            public ResponseEntity<Void> deactivateProfile(AdvertisementDeactivationRequest request) {
//                return null;
//            }
//        };
//    }
//
//    @Bean
//    public FeignOrderService feignOrderService() {
//        return new FeignOrderService() {
//            @Override
//            public ResponseEntity<Void> deactivateOrder(AdvertisementDeactivationRequest request) {
//                return null;
//            }
//        };
//    }

}
