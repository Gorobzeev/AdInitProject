package com.bcgdv.mobileservice.service.java.com.hjb.managementservice.controller.criteria;



import com.bcgdv.mobileservice.controller.model.RegistrationRequest;
import com.bcgdv.mobileservice.service.java.com.hjb.managementservice.common.ContainerInitializer;
import com.bcgdv.mobileservice.service.java.com.hjb.managementservice.common.DockerContainerTest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.function.Predicate;

@Log4j2
@Testcontainers
@ContextConfiguration(classes = {
        CriterionControllerTest.Initializer.class
})
class CriterionControllerTest extends DockerContainerTest {

    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:12")
            .withDatabaseName("adler-local-db")
            .withUsername("admin")
            .withPassword("admin");

    @BeforeAll
    static void startContainers() {
        postgreSQLContainer.start();
        runMigrations(postgreSQLContainer);
    }

    @AfterAll
    static void stopContainers() {
        postgreSQLContainer.stop();
    }

    @DynamicPropertySource
    static void postgresDbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    }

    @Test
    void createUser() {

        RegistrationRequest request =
                new RegistrationRequest("12345678Qwer", "test@mail.com", "+39055234342", "nmae");

        RestAssured.given()
                .with()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post(getUrl("signup"))
                .then().statusCode(200);
    }
//
//    @Test
//    void findAllCategories_OnlyEnabled() {
//        final List<CriterionDTO> dtos = RestAssured.given()
//                .with()
//                .param("enabled", true)
//                .when().get(getUrl(EndPoints.AS_MANAGEMENT_PUBLIC_CRITERIA))
//                .then().statusCode(200)
//                .extract()
//                .as(new TypeRef<List<CriterionDTO>>() {
//                });
//
//        Assertions.assertAll("Actual categories differ from expected",
//                () -> Assertions.assertEquals(4, dtos.size()),
//                () -> Assertions.assertTrue(dtos.stream().allMatch(CriterionDTO::isEnabled))
//        );
//    }
//
//    @Test
//    void findAllCategories_OnlyDisabled() {
//        final List<CriterionDTO> dtos = RestAssured.given()
//                .with()
//                .param("enabled", false)
//                .when().get(getUrl(EndPoints.AS_MANAGEMENT_PUBLIC_CRITERIA))
//                .then().statusCode(200)
//                .extract()
//                .as(new TypeRef<List<CriterionDTO>>() {
//                });
//
//        Assertions.assertAll("Actual categories differ from expected",
//                () -> Assertions.assertEquals(1, dtos.size()),
//                () -> Assertions.assertTrue(dtos.stream().allMatch(Predicate.not(CriterionDTO::isEnabled)))
//        );
//    }
//
//    @Test
//    void findCategoryById_SuccessOne() {
//        final CriterionDTO raw = RestAssured.given()
//                .with()
//                .when().get(getUrl(EndPoints.AS_MANAGEMENT_PUBLIC_CRITERIA + "/" + 1))
//                .then().statusCode(200)
//                .extract()
//                .as(new TypeRef<CriterionDTO>() {
//                });
//
//        try {
//            final CriterionExtendedDTO actual = (CriterionExtendedDTO) raw;
//            Assertions.assertAll("Actual criterion differs from expected",
//                    () -> Assertions.assertNotNull(actual),
//                    () -> Assertions.assertEquals(1, actual.getCriterionId()),
//                    () -> Assertions.assertEquals("Garten", actual.getTitle()),
//                    () -> Assertions.assertEquals(4, actual.getActivities().size()),
//                    () -> Assertions.assertEquals(2, actual.getRequirements().size())
//            );
//        } catch (ClassCastException e) {
//            Assert.fail();
//        }
//    }
//
//    @Test
//    void findCategoryById_SuccessTwo() {
//        final CriterionDTO raw = RestAssured.given()
//                .with()
//                .when().get(getUrl(EndPoints.AS_MANAGEMENT_PUBLIC_CRITERIA + "/" + 2))
//                .then().statusCode(200)
//                .extract()
//                .as(new TypeRef<CriterionDTO>() {
//                });
//
//        try {
//            final CriterionExtendedDTO actual = (CriterionExtendedDTO) raw;
//            Assertions.assertAll("Actual criterion differs from expected",
//                    () -> Assertions.assertNotNull(actual),
//                    () -> Assertions.assertEquals(2, actual.getCriterionId()),
//                    () -> Assertions.assertEquals("Haushalt", actual.getTitle()),
//                    () -> Assertions.assertEquals(8, actual.getActivities().size()),
//                    () -> Assertions.assertEquals(7, actual.getEditableOptions().size()),
//                    () -> Assertions.assertEquals(2, actual.getRequirements().size())
//            );
//        } catch (ClassCastException e) {
//            Assert.fail();
//        }
//    }
//
//    @Test
//    void findCategoryById_SuccessFive() {
//        final CriterionDTO raw = RestAssured.given()
//                .with()
//                .when().get(getUrl(EndPoints.AS_MANAGEMENT_PUBLIC_CRITERIA + "/" + 5))
//                .then().statusCode(200)
//                .extract()
//                .as(new TypeRef<CriterionDTO>() {
//                });
//
//        try {
//            final CriterionExtendedDTO actual = (CriterionExtendedDTO) raw;
//            Assertions.assertAll("Actual criterion differs from expected",
//                    () -> Assertions.assertNotNull(actual),
//                    () -> Assertions.assertEquals(5, actual.getCriterionId()),
//                    () -> Assertions.assertEquals("Tierbetreuung", actual.getTitle()),
//                    () -> Assertions.assertEquals(5, actual.getSubCriteria().size()),
//                    () -> Assertions.assertEquals(5, actual.getSubCriteria().stream().map(sb -> (SubCriterionExtendedDTO)sb).map(sb -> sb.getActivities().size()).count()),
//                    () -> Assertions.assertEquals(2, actual.getRequirements().size())
//            );
//        } catch (ClassCastException e) {
//            Assert.fail();
//        }
//    }
//
//    @Test
//    void findCategoryById_NotFound() {
//        final ErrorResponseDTO error = RestAssured.given()
//                .with()
//                .when().get(getUrl(EndPoints.AS_MANAGEMENT_PUBLIC_CRITERIA + "/" + 100))
//                .then().statusCode(404)
//                .extract()
//                .as(new TypeRef<ErrorResponseDTO>() {
//                });
//
//        Assertions.assertAll("Missing criterion should have defined error",
//                () -> Assertions.assertNotNull(error),
//                () -> Assertions.assertEquals("404 NOT_FOUND", error.getStatus()),
//                () -> Assertions.assertEquals(ErrorCodes.RESOURCE_NOT_FOUND_EXCEPTION.name(), error.getType()),
//                () -> Assertions.assertEquals(ErrorCodes.RESOURCE_NOT_FOUND_EXCEPTION.getMessage(), error.getMessage()),
//                () -> Assertions.assertEquals("Requested resource of type [Criterion] with identifier 100 not found", error.getDetails())
//        );
//    }

    @Nested
    class CriterionExchangeControllerTest {
//
//        @Test
//        public void findAllExchangedCategories() {
//            final List<CriterionDTO> dtos = RestAssured.given()
//                    .with()
//                    .param("enabled", true)
//                    .when().get(getUrl(EndPoints.AS_MANAGEMENT_PUBLIC_CRITERIA + "/exchange"))
//                    .then().statusCode(200)
//                    .extract()
//                    .as(new TypeRef<List<CriterionDTO>>() {
//                    });
//
//            Assertions.assertAll("Actual categories differ from expected",
//                    () -> Assertions.assertEquals(4, dtos.size()),
//                    () -> Assertions.assertTrue(dtos.stream().allMatch(CriterionDTO::isEnabled))
//            );
//        }
    }


//    @Nested
//    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//    class CriterionModifyControllerTest {
//
//        @BeforeEach
//        public void authenticateAdmin() {
//            authenticateAppAdmin();
//        }
//
//        @Test
//        @Order(1)
//        void createCategory() {
//            final CriterionModifyRequest request = CriterionModifyRequest.builder()
//                    .title("Test criterion")
//                    .ordinal(8)
//                    .enabled(true)
//                    .build();
//            final CriterionDTO actual = RestAssured.given()
//                    .with()
//                    .headers(getAuthorizationHeaders(ADMIN_OAUTH_TOKEN))
//                    .body(request)
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .when().post(getUrl(EndPoints.AS_MANAGEMENT_CRITERIA))
//                    .then().statusCode(200)
//                    .extract()
//                    .as(CriterionDTO.class);
//
//            Assertions.assertAll("Actual created criterion differs from expected",
//                    () -> Assertions.assertNotNull(actual),
//                    () -> Assertions.assertEquals(6, actual.getCriterionId()),
//                    () -> Assertions.assertEquals("Test criterion", actual.getTitle())
//            );
//        }
//
//        @Test
//        @Order(2)
//        void updateCategoryBuId() {
//            final CriterionModifyRequest request = CriterionModifyRequest.builder()
//                    .title("Dog Breeding")
//                    .enabled(true)
//                    .ordinal(8)
//                    .build();
//            final CriterionDTO actual = RestAssured.given()
//                    .with()
//                    .headers(getAuthorizationHeaders(ADMIN_OAUTH_TOKEN))
//                    .body(request)
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .when().put(getUrl(EndPoints.AS_MANAGEMENT_CRITERIA + "/" + 3))
//                    .then().statusCode(200)
//                    .extract()
//                    .as(CriterionDTO.class);
//
//            Assertions.assertAll("Actual created criterion differs from expected",
//                    () -> Assertions.assertNotNull(actual),
//                    () -> Assertions.assertEquals(3, actual.getCriterionId()),
//                    () -> Assertions.assertTrue(actual.isEnabled())
//            );
//        }
//
//        @Test
//        @Order(3)
//        void deleteCategoryById() {
//            RestAssured.given()
//                    .with()
//                    .headers(getAuthorizationHeaders(ADMIN_OAUTH_TOKEN))
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .when().delete(getUrl(EndPoints.AS_MANAGEMENT_CRITERIA + "/" + 3))
//                    .then().statusCode(202);
//        }
//
//        @Test
//        @Order(4)
//        void updateCategoryBuId_NotFound() {
//            final CriterionModifyRequest request = CriterionModifyRequest.builder()
//                    .title("Dog Breeding")
//                    .enabled(true)
//                    .ordinal(10)
//                    .build();
//            final ErrorResponseDTO error = RestAssured.given()
//                    .with()
//                    .headers(getAuthorizationHeaders(ADMIN_OAUTH_TOKEN))
//                    .body(request)
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .when().put(getUrl(EndPoints.AS_MANAGEMENT_CRITERIA + "/" + 100))
//                    .then().statusCode(404)
//                    .extract()
//                    .as(ErrorResponseDTO.class);
//
//            Assertions.assertAll("Missing criterion should have defined error",
//                    () -> Assertions.assertNotNull(error),
//                    () -> Assertions.assertEquals("404 NOT_FOUND", error.getStatus()),
//                    () -> Assertions.assertEquals(ErrorCodes.RESOURCE_NOT_FOUND_EXCEPTION.name(), error.getType()),
//                    () -> Assertions.assertEquals(ErrorCodes.RESOURCE_NOT_FOUND_EXCEPTION.getMessage(), error.getMessage()),
//                    () -> Assertions.assertEquals("Requested resource of type [Criterion] with identifier 100 not found", error.getDetails())
//            );
//        }
//
//    }

    static class Initializer extends ContainerInitializer {
        public Initializer() {
            super(postgreSQLContainer);
        }
    }
}
