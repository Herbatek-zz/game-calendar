package com.piotrek.gamecalendar;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.annotation.Resource;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = GameCalendarApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

    private final static String DB_PREFIX = "jdbc:mariadb://";
    private final static String DB_NAME = "testDB";
    private final static String DB_USERNAME = "userTest";
    private final static String DB_PASSWORD = "strongPassword";
    private final static int DB_PORT = 3306;

    @Resource
    protected WebTestClient webTestClient;

    @Resource
    protected ObjectMapper objectMapper;

    private static final GenericContainer MARIA_DB_CONTAINER;

    static {
        MARIA_DB_CONTAINER = new MariaDBContainer()
                .withDatabaseName(DB_NAME)
                .withUsername(DB_USERNAME)
                .withPassword(DB_PASSWORD)
                .withExposedPorts(DB_PORT);

        MARIA_DB_CONTAINER.start();
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            String dbUrl = DB_PREFIX +
                    MARIA_DB_CONTAINER.getContainerIpAddress() +
                    ":" + MARIA_DB_CONTAINER.getMappedPort(DB_PORT) +
                    "/" + DB_NAME;

            TestPropertyValues values = TestPropertyValues.of(
                    "spring.datasource.url=" + dbUrl,
                    "spring.datasource.username=" + DB_USERNAME,
                    "spring.datasource.password=" + DB_PASSWORD
            );
            values.applyTo(configurableApplicationContext);
        }
    }
}
