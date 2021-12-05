package de.renkensoftware.hbc.integrationtests;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.transaction.Transactional;

@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DatabaseRelatedTest {

//    @Autowired
//    private UserJpaRepository userJpaRepository;

    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:13.3")
            .withDatabaseName("nawi").withUsername("root").withPassword("geheim123");

    @DynamicPropertySource
    static void postgresqlProperties(final DynamicPropertyRegistry registry) {
        POSTGRESQL_CONTAINER.start();
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.liquibase.password", POSTGRESQL_CONTAINER::getPassword);
        registry.add("spring.liquibase.user", POSTGRESQL_CONTAINER::getUsername);
    }

//    @BeforeEach
//    void cleanUp() {
//        userJpaRepository.deleteAll();
//        userJpaRepository.flush();
//    }
}
