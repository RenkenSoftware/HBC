package de.renkensoftware.hbc.integrationtests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.renkensoftware.hbc.domain.user.application.viewobjects.UserAddFriendVo;
import de.renkensoftware.hbc.domain.user.application.viewobjects.UserCreationVo;
import de.renkensoftware.hbc.domain.user.application.viewobjects.UserIdVo;
import de.renkensoftware.hbc.domain.user.infrastructure.UserJpaRepository;
import de.renkensoftware.hbc.domain.user.infrastructure.entity.UserEntity;
import de.renkensoftware.hbc.exception.ResponseError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        UserCreationVo userCreationVo = new UserCreationVo();
        userCreationVo.setEmail("email");
        userCreationVo.setPassword("password");

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(userCreationVo);

        mock.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status()
                        .isCreated());

        Optional<UserEntity> userEntityOptional = userJpaRepository.findAll().stream().findFirst();

        assertThat(userEntityOptional).isPresent();

        assertThat(userEntityOptional.get().getId()).isNotNull();
        assertThat(userEntityOptional.get().getEmail()).isEqualTo("email");
        assertThat(userEntityOptional.get().getPassword()).isEqualTo("password");
    }

    @Test
    void findUserIdByEmail() throws Exception {
        UUID id = UUID.randomUUID();
        String email = "email";
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setEmail(email);
        userEntity.setPassword("password");
        userEntity.setName("name");

        userJpaRepository.save(userEntity);

        MvcResult result = mock.perform(get("/user/email").contentType(MediaType.APPLICATION_JSON)
                        .content(email))
                .andExpect(status()
                        .isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();

        UserIdVo userIdVo = objectMapper.readValue(jsonResponse, UserIdVo.class);

        assertThat(userIdVo.getId()).isEqualTo(id);
    }

    @Test
    void findUserIdByNonExistingEmailThrowsException() throws Exception {
        MvcResult result = mock.perform(get("/user/email").contentType(MediaType.APPLICATION_JSON)
                        .content("email"))
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
        UUID id = UUID.randomUUID();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setEmail("email");
        userEntity.setPassword("password");
        userEntity.setName("name");

        UUID friendId = UUID.randomUUID();
        UserEntity friendEntity = new UserEntity();
        friendEntity.setId(friendId);
        friendEntity.setEmail("friendemail");
        friendEntity.setPassword("friendpassword");
        friendEntity.setName("friendname");

        userJpaRepository.save(userEntity);
        userJpaRepository.save(friendEntity);

        UserAddFriendVo userAddFriendVo = new UserAddFriendVo();
        userAddFriendVo.setId(id);
        userAddFriendVo.setFriendId(friendId);

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(userAddFriendVo);

        mock.perform(put("/user/addfriend").contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status()
                        .isAccepted());

        Optional<UserEntity> userEntityOptional = userJpaRepository.findById(id);

        assertThat(userEntityOptional).isPresent();

        assertThat(userEntityOptional.get().getFriends().iterator().next().getId()).isEqualTo(friendId);

        Optional<UserEntity> friendEntityOptional = userJpaRepository.findById(friendId);

        assertThat(friendEntityOptional).isPresent();

        assertThat(friendEntityOptional.get().getFriends().iterator().next().getId()).isEqualTo(id);
    }
}
