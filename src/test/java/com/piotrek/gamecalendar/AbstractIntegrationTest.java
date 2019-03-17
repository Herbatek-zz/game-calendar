package com.piotrek.gamecalendar;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = GameCalendarApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

    @Container
    private static final GenericContainer MARIA_DB_CONTAINER = new MariaDBContainer()
            .withDatabaseName("testDB")
            .withUsername("userTest")
            .withPassword("strongPassword")
            .withExposedPorts(3306);

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            String url = "=jdbc:mariadb://" +
                    MARIA_DB_CONTAINER.getContainerIpAddress() +
                    ":" +
                    MARIA_DB_CONTAINER.getMappedPort(3306) +
                    "/testDB";
            System.out.println(MARIA_DB_CONTAINER.getMappedPort(3306));
            TestPropertyValues values = TestPropertyValues.of("spring.datasource.url" + url);
            values.applyTo(configurableApplicationContext);
        }
    }
}
