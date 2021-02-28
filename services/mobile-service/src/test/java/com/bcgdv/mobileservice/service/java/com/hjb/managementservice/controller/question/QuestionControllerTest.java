package com.bcgdv.mobileservice.service.java.com.hjb.managementservice.controller.question;//package com.bcgdv.mobileservice.service.java.com.hjb.managementservice.controller.question;
//
//import com.hjb.managementservice.common.ContainerInitializer;
//import com.hjb.managementservice.common.DockerContainerTest;
//import com.hjb.sharedservice.enums.AnswerInputType;
//import com.hjb.sharedservice.enums.OptionType;
//import com.hjb.sharedservice.utils.EndPoints;
//import com.hjb.sharedservice.web.response.AnswerDto;
//import com.hjb.sharedservice.web.response.QuestionDto;
//import io.restassured.RestAssured;
//import io.restassured.mapper.TypeRef;
//import lombok.extern.log4j.Log4j2;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
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
//		QuestionControllerTest.Initializer.class
//})
//public class QuestionControllerTest extends DockerContainerTest {
//
//	@Container
//	public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11")
//			.withDatabaseName("hjb-app")
//			.withUsername("app_user")
//			.withPassword("12345678");
//
//	@BeforeAll
//	static void startContainers() {
//		postgreSQLContainer.start();
//		runMigrations(postgreSQLContainer);
//	}
//
//	@AfterAll
//	static void stopContainers() {
//		postgreSQLContainer.stop();
//	}
//
//	@DynamicPropertySource
//	static void postgresDbProperties(final DynamicPropertyRegistry registry) {
//		registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
//	}
//
//	@Test
//	void findAllQuestion() {
//		final List<QuestionDto> dtos = RestAssured.given()
//				.with()
//				.when().get(this.getUrl(EndPoints.AS_MANAGEMENT_PUBLIC_QUESTION))
//				.then().statusCode(200)
//				.extract()
//				.as(new TypeRef<List<QuestionDto>>() {
//				});
//
//		Assertions.assertAll("Actual questions differ from expected",
//				() -> Assertions.assertEquals(1, dtos.size()));
//	}
//
////	@Test
//	void findQuestionById() {
//		final QuestionDto question = RestAssured.given()
//				.with()
//				.when().get(this.getUrl(EndPoints.AS_MANAGEMENT_PUBLIC_QUESTION + "/1"))
//				.then().statusCode(200)
//				.extract()
//				.as(new TypeRef<QuestionDto>() {
//				});
//
//		Assertions.assertAll("Actual questions differ from expected",
//				() -> Assertions.assertEquals("Question 1", question.getTitle()),
//				() -> Assertions.assertEquals(2, question.getAnswers().size())
//		);
//	}
//
//	@Test
//	void createQuestionTest() {
//		this.authenticateAppAdmin();
//		final QuestionDto request = QuestionDto.builder()
//				.title("New question")
//				.build();
//
//		final QuestionDto question = RestAssured.given()
//				.with()
//				.headers(this.getAuthorizationHeaders(ADMIN_OAUTH_TOKEN))
//				.body(request)
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.when().post(this.getUrl(EndPoints.AS_MANAGEMENT_QUESTION))
//				.then().statusCode(200)
//				.extract()
//				.as(new TypeRef<QuestionDto>() {
//				});
//
//		Assertions.assertAll("Question differ from expected",
//				() -> Assertions.assertEquals("New question", question.getTitle()),
//				() -> Assertions.assertNotNull(question.getId()),
//				() -> Assertions.assertTrue(question.getEnabled())
//		);
//	}
//
//	@Test
//	void updateQuestion() {
//		this.authenticateAppAdmin();
//		final QuestionDto request = QuestionDto.builder()
//				.title("Question 1 changed")
//				.enabled(false)
//				.build();
//
//		final QuestionDto question = RestAssured.given()
//				.with()
//				.headers(this.getAuthorizationHeaders(ADMIN_OAUTH_TOKEN))
//				.body(request)
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.when().put(this.getUrl(EndPoints.AS_MANAGEMENT_QUESTION + "/1"))
//				.then().statusCode(200)
//				.extract()
//				.as(new TypeRef<QuestionDto>() {
//				});
//
//		Assertions.assertAll("Question differ from expected",
//				() -> Assertions.assertEquals("Question 1 changed", question.getTitle()),
//				() -> Assertions.assertNotNull(question.getId()),
//				() -> Assertions.assertEquals(1, question.getId()),
//				() -> Assertions.assertFalse(question.getEnabled())
//		);
//	}
//
//	@Test
//	void deleteQuestion() {
//		this.authenticateAppAdmin();
//		final List<QuestionDto> response = RestAssured.given()
//				.with()
//				.headers(this.getAuthorizationHeaders(ADMIN_OAUTH_TOKEN))
//				.when().delete(this.getUrl(EndPoints.AS_MANAGEMENT_QUESTION + "/1"))
//				.then().statusCode(200)
//				.extract()
//				.as(new TypeRef<List<QuestionDto>>() {
//				});
//
//		Assertions.assertAll("Question differ from expected",
//				() -> Assertions.assertEquals(0, response.size()));
//	}
//
////	@Test
//	void addAnswerTest() {
//		this.authenticateAppAdmin();
//		final AnswerDto request = AnswerDto.builder()
//				.content("Answer 3")
//				.type(AnswerInputType.checkbox)
//				.build();
//
//		final QuestionDto question = RestAssured.given()
//				.with()
//				.headers(this.getAuthorizationHeaders(ADMIN_OAUTH_TOKEN))
//				.body(request)
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.when().post(this.getUrl(EndPoints.AS_MANAGEMENT_QUESTION + "/1/answer"))
//				.then().statusCode(200)
//				.extract()
//				.as(new TypeRef<QuestionDto>() {
//				});
//
//		Assertions.assertAll("Question differ from expected",
//				() -> Assertions.assertEquals("Question 1", question.getTitle()),
//				() -> Assertions.assertNotNull(question.getId()),
//				() -> Assertions.assertEquals(3, question.getAnswers().size()),
//				() -> Assertions.assertNotNull(question.getAnswers().get(2).getId()),
//				() -> Assertions.assertEquals("Answer 3", question.getAnswers().get(2).getContent()),
//				() -> Assertions.assertEquals(3, question.getAnswers().get(2).getOrdinal())
//		);
//	}
//
//	@Test
//	void deleteAnswer() {
//		this.authenticateAppAdmin();
//		final List<AnswerDto> result = RestAssured.given()
//				.with()
//				.headers(this.getAuthorizationHeaders(ADMIN_OAUTH_TOKEN))
//				.when().delete(this.getUrl(EndPoints.AS_MANAGEMENT_ANSWER + "/1"))
//				.then().statusCode(200)
//				.extract()
//				.as(new TypeRef<List<AnswerDto>>() {
//				});
//
//		Assertions.assertAll("Question differ from expected",
//				() -> Assertions.assertEquals(6, result.size()),
//				() -> Assertions.assertEquals("Homepage oder Soziale Medien der Minijob-Zentrale", result.get(0).getContent()),
//				() -> Assertions.assertEquals(1, result.get(0).getOrdinal())
//		);
//	}
//
////	@Test
//	void changeAnswerOrderTest() {
//		this.authenticateAppAdmin();
//		final List<AnswerDto> result = RestAssured.given()
//				.with()
//				.headers(this.getAuthorizationHeaders(ADMIN_OAUTH_TOKEN))
//				.when().post(this.getUrl(EndPoints.AS_MANAGEMENT_ANSWER + "/2/1"))
//				.then().statusCode(200)
//				.extract()
//				.as(new TypeRef<List<AnswerDto>>() {
//				});
//
//		Assertions.assertAll("Question differ from expected",
//				() -> Assertions.assertEquals(2, result.size()),
//				() -> Assertions.assertEquals("Answer 1", result.get(0).getContent()),
//				() -> Assertions.assertEquals(2, result.get(0).getOrdinal()),
//				() -> Assertions.assertEquals("Answer 2", result.get(1).getContent()),
//				() -> Assertions.assertEquals(1, result.get(1).getOrdinal())
//		);
//	}
//
//	static class Initializer extends ContainerInitializer {
//		public Initializer() {
//			super(postgreSQLContainer);
//		}
//	}
//}
