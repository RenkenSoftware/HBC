package de.renkensoftware.hbc.integrationtests;

import de.renkensoftware.hbc.domain.user.infrastructure.UserJpaRepository;
import de.renkensoftware.hbc.domain.user.infrastructure.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@WebAppConfiguration
class UserIT extends DatabaseRelatedTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    UserJpaRepository userJpaRepository;

    private MockMvc mock;

    @BeforeEach
    public void setup() {
        mock = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }
    @Test
    void createNewUser() throws Exception {

        String json = "{\"email\":\"myemail\",\"password\":\"mypassword\"}";

        mock.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status()
                        .isCreated());

        Optional<UserEntity> userEntityOptional = userJpaRepository.findAll().stream().findFirst();

        assertThat(userEntityOptional).isPresent();

        assertThat(userEntityOptional.get().getId()).isNotNull();
        assertThat(userEntityOptional.get().getEmail()).isEqualTo("myemail");
        assertThat(userEntityOptional.get().getPassword()).isEqualTo("mypassword");
    }
}
