package de.renkensoftware.hbc.integrationtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.renkensoftware.hbc.domain.authentication.application.viewobjects.AuthenticationVo;
import de.renkensoftware.hbc.domain.authentication.application.viewobjects.TokenVo;
import de.renkensoftware.hbc.domain.user.application.viewobjects.UserAddFriendVo;
import de.renkensoftware.hbc.domain.user.application.viewobjects.UserCreationVo;
import de.renkensoftware.hbc.domain.user.application.viewobjects.UserIdVo;
import de.renkensoftware.hbc.domain.user.infrastructure.UserJpaRepository;
import de.renkensoftware.hbc.domain.user.infrastructure.entity.UserEntity;
import de.renkensoftware.hbc.exception.ResponseError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;

import java.util.Optional;

import static de.renkensoftware.hbc.testdatafactories.AuthenticationTestDataFactory.createAuthenticationVo;
import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
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
                .apply(springSecurity())
                .build();
    }

    private String createTestTokenString() throws Exception {
        UserEntity userEntity = createUserEntityWithoutFriend();

        userJpaRepository.save(userEntity);

        AuthenticationVo authenticationVo = createAuthenticationVo();

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(authenticationVo);

        MvcResult result = mock.perform(post("/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        return objectMapper.readValue(response, TokenVo.class).getTokenString();
    }

    @Test
    void createNewUser() throws Exception {
        UserCreationVo userCreationVo = createUserCreationVo();

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(userCreationVo);

        mock.perform(post("/user/create").contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status()
                        .isCreated());

        Optional<UserEntity> userEntityOptional = userJpaRepository.findAll().stream().findFirst();

        assertThat(userEntityOptional).isPresent();

        assertThat(userEntityOptional.get().getId()).isNotNull();
        assertThat(userEntityOptional.get().getEmail()).isEqualTo(EMAIL);
        assertThat(userEntityOptional.get().getPassword()).isEqualTo(PASSWORD);
    }

    @Test
    void findUserIdByEmail() throws Exception {
        String tokenString = createTestTokenString();

        MvcResult result = mock.perform(get("/user/email")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(EMAIL))
                .andExpect(status()
                        .isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();

        UserIdVo userIdVo = objectMapper.readValue(jsonResponse, UserIdVo.class);

        assertThat(userIdVo.getId()).isEqualTo(USER_ID);
    }

    @Test
    void findUserIdByNonExistingEmailThrowsException() throws Exception {
        String tokenString = createTestTokenString();

        MvcResult result = mock.perform(get("/user/email")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("wrongemail"))
                .andExpect(status()
                        .isNotFound())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();

        ResponseError responseError = objectMapper.readValue(jsonResponse, ResponseError.class);

        assertThat(responseError.code()).isEqualTo(1);
        assertThat(responseError.message()).isEqualTo("User not found");
    }

    @Test
    void addFriend() throws Exception {
        String tokenString = createTestTokenString();

        userJpaRepository.save(createFriendEntity());

        UserAddFriendVo userAddFriendVo = createUserAddFriendVo();

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(userAddFriendVo);

        mock.perform(put("/user/addfriend")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status()
                        .isAccepted());

        Optional<UserEntity> userEntityOptional = userJpaRepository.findById(USER_ID);

        assertThat(userEntityOptional).isPresent();

        assertThat(userEntityOptional.get().getFriends().iterator().next().getId()).isEqualTo(FRIEND_ID);

        Optional<UserEntity> friendEntityOptional = userJpaRepository.findById(FRIEND_ID);

        assertThat(friendEntityOptional).isPresent();

        assertThat(friendEntityOptional.get().getFriends().iterator().next().getId()).isEqualTo(USER_ID);
    }
}
