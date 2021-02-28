package com.bcgdv.mobileservice.service.java.com.hjb.managementservice.common;

import com.bcgdv.mobileservice.MobileServiceApplication;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import lombok.extern.log4j.Log4j2;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.io.File;

/**
 * Read more about int testing
 * https://programmerfriend.com/spring-boot-integration-testing-done-right/
 * <p>
 * RestAssured API
 * https://www.baeldung.com/rest-assured-header-cookie-parameter
 */
@Log4j2
@ActiveProfiles({"test"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = {
                TestConfig.class, MobileServiceApplication.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@EnableConfigurationProperties({TestProperties.class})
public abstract class DockerContainerTest {

    protected static final String SUPER_ADMIN_OAUTH_TOKEN = "SUPER_ADMIN_OAUTH_TOKEN";
    protected static final String ADMIN_OAUTH_TOKEN = "ADMIN_OAUTH_TOKEN";
    protected static final String USER_OAUTH_TOKEN = "USER_OAUTH_TOKEN";

    @Value("http://localhost:${local.server.port}")
    protected String baseUrl;

    @Autowired
    private TestProperties testProperties;


    protected Headers getAuthorizationHeaders(final String token) {
        try {
            final Header authHeader = new Header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token));
            return new Headers(authHeader);
        } catch (final Exception e) {
            log.error("Failed to get test auth header: ", e);
            return null;
        }
    }

    protected String getUrl(final String path) {
        if (path.startsWith("/")) {
            return this.baseUrl;
        }
        return this.baseUrl + "/" + path;
    }

    protected static void runMigrations(final PostgreSQLContainer container) {
        final Flyway flyway = new Flyway(Flyway.configure()
                .dataSource(getDataSource(container))
                .schemas("public")
                .baselineOnMigrate(true)
                .locations(
                        new Location(getDBMigrationsAbsolutePath("migration")))
        );
        flyway.migrate();
    }

    private static String getDBMigrationsAbsolutePath(final String folderName) {
        return "filesystem:" + new File(new ClassPathResource("/src/main/resources/db/" + folderName).getPath()).getAbsolutePath();
    }

    protected static DataSource getDataSource(final PostgreSQLContainer container) {
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(container.getJdbcUrl());
        hikariConfig.setUsername(container.getUsername());
        hikariConfig.setPassword(container.getPassword());
        return new HikariDataSource(hikariConfig);
    }

}
