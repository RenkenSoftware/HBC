package de.renkensoftware.hbc.integrationtests;

import de.renkensoftware.hbc.domain.user.infrastructure.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.transaction.Transactional;

@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DatabaseRelatedTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    private static final PostgreSQLContainer POSTGRESQL_CONTAINER = new PostgreSQLContainer("postgres:13.3")
            .withDatabaseName("test_db").withUsername("test_user").withPassword("test_pw");

    @DynamicPropertySource
    static void postgresqlProperties(final DynamicPropertyRegistry registry) {
        POSTGRESQL_CONTAINER.start();
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
    }

    @BeforeEach
    void tidyUp() {
        userJpaRepository.deleteAll();
        userJpaRepository.flush();
    }
}
