package com.bcgdv.mobileservice.service.java.com.hjb.managementservice.controller.criteria;//package com.bcgdv.mobileservice.service.java.com.hjb.managementservice.controller.criteria;
//
//import com.hjb.managementservice.common.ContainerInitializer;
//import com.hjb.managementservice.common.DockerContainerTest;
//import com.hjb.sharedservice.exceptions.ErrorCodes;
//import com.hjb.sharedservice.exceptions.ErrorResponseDTO;
//import com.hjb.sharedservice.utils.EndPoints;
//import com.hjb.sharedservice.web.request.criteria.SubCriterionModifyRequest;
//import com.hjb.sharedservice.web.response.criteria.SubCriterionStandardDTO;
//import io.restassured.RestAssured;
//import io.restassured.mapper.TypeRef;
//import lombok.extern.log4j.Log4j2;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.List;
//
//@Log4j2
//@Testcontainers
//@ContextConfiguration(classes = {
//        SubCriteriaReadControllerTest.Initializer.class
//})
//class SubCriteriaReadControllerTest extends DockerContainerTest {
//
//    @Container
//    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11")
//            .withDatabaseName("hjb-app")
//            .withUsername("app_user")
//            .withPassword("12345678");
//
//    @BeforeAll
//    static void startContainers() {
//        postgreSQLContainer.start();
//        runMigrations(postgreSQLContainer);
//    }
//
//    @AfterAll
//    static void stopContainers() {
//        postgreSQLContainer.stop();
//    }
//
//    @DynamicPropertySource
//    static void postgresDbProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
//    }
//
//    @Test
//    void findAllByParentId_Persent() {
//        final List<SubCriterionStandardDTO> dtos = RestAssured.given()
//                .with()
//                .when().get(getUrl(EndPoints.AS_MANAGEMENT_PUBLIC_SUB_CRITERIA + "/" + 5))
//                .then().statusCode(200)
//                .extract()
//                .as(new TypeRef<List<SubCriterionStandardDTO>>() {
//                });
//
//        Assertions.assertAll("Actual sub-criteria differ from expected",
//                () -> Assertions.assertEquals(6, dtos.size()),
//                () -> Assertions.assertTrue(dtos.stream().anyMatch(subKriteriumDTO -> subKriteriumDTO.getTitle().equalsIgnoreCase("Katze")))
//        );
//    }
//
//    @Test
//    void findAllByParentId_Missing() {
//        final List<SubCriterionStandardDTO> dtos = RestAssured.given()
//                .with()
//                .when().get(getUrl(EndPoints.AS_MANAGEMENT_PUBLIC_SUB_CRITERIA + "/" + 1))
//                .then().statusCode(200)
//                .extract()
//                .as(new TypeRef<List<SubCriterionStandardDTO>>() {
//                });
//
//        Assertions.assertAll("Actual sub-criteria differ from expected",
//                () -> Assertions.assertTrue(dtos.isEmpty())
//        );
//    }
//
//    @Test
//    void findOneById_Success() {
//        final SubCriterionStandardDTO actual = RestAssured.given()
//                .with()
//                .when().get(getUrl(EndPoints.AS_MANAGEMENT_PUBLIC_SUB_CRITERIA + "/single/" + 100000))
//                .then().statusCode(200)
//                .extract()
//                .as(new TypeRef<SubCriterionStandardDTO>() {
//                });
//
//        Assertions.assertAll("Actual sub-criteria differ from expected",
//                () -> Assertions.assertNotNull(actual),
//                () -> Assertions.assertEquals("Katze", actual.getTitle()),
//                () -> Assertions.assertEquals(5, actual.getCriterionId())
//        );
//    }
//
//    @Test
//    void findOneById_NotFound() {
//        final ErrorResponseDTO error = RestAssured.given()
//                .with()
//                .when().get(getUrl(EndPoints.AS_MANAGEMENT_PUBLIC_SUB_CRITERIA + "/single/" + 100))
//                .then().statusCode(404)
//                .extract()
//                .as(new TypeRef<ErrorResponseDTO>() {
//                });
//
//        Assertions.assertAll("Missing sub-criterion should have defined error",
//                () -> Assertions.assertNotNull(error),
//                () -> Assertions.assertEquals("404 NOT_FOUND", error.getStatus()),
//                () -> Assertions.assertEquals(ErrorCodes.RESOURCE_NOT_FOUND_EXCEPTION.name(), error.getType()),
//                () -> Assertions.assertEquals(ErrorCodes.RESOURCE_NOT_FOUND_EXCEPTION.getMessage(), error.getMessage()),
//                () -> Assertions.assertEquals("Requested resource of type [SubCriterion] with identifier 100 not found", error.getDetails())
//        );
//    }
//
//    @Nested
//    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//    class SubCriteriaModifyControllerTest {
//
//        @BeforeEach
//        public void authenticateAdmin() {
//            authenticateAppAdmin();
//        }
//
//        @Test
//        @Order(1)
//        void createSubCriterion() {
//            final SubCriterionStandardDTO actual = RestAssured.given()
//                    .with()
//                    .headers(getAuthorizationHeaders(ADMIN_OAUTH_TOKEN))
//                    .body(testRequest("Test sub-criterion"))
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .when().post(getUrl(EndPoints.AS_MANAGEMENT_SUB_CRITERIA))
//                    .then().statusCode(200)
//                    .extract()
//                    .as(SubCriterionStandardDTO.class);
//
//            Assertions.assertAll("Actual created sub-criterion differs from expected",
//                    () -> Assertions.assertNotNull(actual),
//                    () -> Assertions.assertEquals(100006, actual.getId()),
//                    () -> Assertions.assertEquals("Test sub-criterion", actual.getTitle())
//            );
//        }
//
//        @Test
//        @Order(2)
//        void updateSubCriterionByBuId() {
//            final SubCriterionModifyRequest updateRequest = testRequest("Updated");
//            final SubCriterionStandardDTO actual = RestAssured.given()
//                    .with()
//                    .headers(getAuthorizationHeaders(ADMIN_OAUTH_TOKEN))
//                    .body(updateRequest)
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .when().put(getUrl(EndPoints.AS_MANAGEMENT_SUB_CRITERIA + "/" + 100000))
//                    .then().statusCode(200)
//                    .extract()
//                    .as(SubCriterionStandardDTO.class);
//
//            Assertions.assertAll("Actual created criterion differs from expected",
//                    () -> Assertions.assertNotNull(actual),
//                    () -> Assertions.assertEquals(100000, actual.getId()),
//                    () -> Assertions.assertEquals(updateRequest.getTitle(), actual.getTitle()),
//                    () -> Assertions.assertTrue(actual.isEnabled())
//            );
//        }
//
//        @Test
//        @Order(3)
//        void deleteSubCriterionById() {
//            RestAssured.given()
//                    .with()
//                    .headers(getAuthorizationHeaders(ADMIN_OAUTH_TOKEN))
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .when().delete(getUrl(EndPoints.AS_MANAGEMENT_SUB_CRITERIA + "/" + 100006))
//                    .then().statusCode(200);
//        }
//
//        @Test
//        @Order(4)
//        void updateCategoryBuId_NotFound() {
//            final SubCriterionModifyRequest request = testRequest("Dog Breeding");
//            final ErrorResponseDTO error = RestAssured.given()
//                    .with()
//                    .headers(getAuthorizationHeaders(ADMIN_OAUTH_TOKEN))
//                    .body(request)
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .when().put(getUrl(EndPoints.AS_MANAGEMENT_SUB_CRITERIA + "/" + 100))
//                    .then().statusCode(404)
//                    .extract()
//                    .as(ErrorResponseDTO.class);
//
//            Assertions.assertAll("Missing criterion should have defined error",
//                    () -> Assertions.assertNotNull(error),
//                    () -> Assertions.assertEquals("404 NOT_FOUND", error.getStatus()),
//                    () -> Assertions.assertEquals(ErrorCodes.RESOURCE_NOT_FOUND_EXCEPTION.name(), error.getType()),
//                    () -> Assertions.assertEquals(ErrorCodes.RESOURCE_NOT_FOUND_EXCEPTION.getMessage(), error.getMessage()),
//                    () -> Assertions.assertEquals("Requested resource of type [SubCriterion] with identifier 100 not found", error.getDetails())
//            );
//        }
//
//        private SubCriterionModifyRequest testRequest(String title) {
//            return SubCriterionModifyRequest.builder()
//                    .title(title)
//                    .kriteriumId(1)
//                    .enabled(true)
//                    .build();
//        }
//    }
//
//    static class Initializer extends ContainerInitializer {
//        public Initializer() {
//            super(postgreSQLContainer);
//        }
//    }
//}
