package de.renkensoftware.hbc.integrationtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.renkensoftware.hbc.domain.authentication.application.viewobjects.AuthenticationVo;
import de.renkensoftware.hbc.domain.authentication.application.viewobjects.TokenVo;
import de.renkensoftware.hbc.domain.room.application.viewobjects.RoomCreationVo;
import de.renkensoftware.hbc.domain.room.infrastructure.RoomJpaRepository;
import de.renkensoftware.hbc.domain.room.infrastructure.entity.RoomEntity;
import de.renkensoftware.hbc.domain.user.infrastructure.UserJpaRepository;
import de.renkensoftware.hbc.domain.user.infrastructure.entity.UserEntity;
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
import static de.renkensoftware.hbc.testdatafactories.RoomTestDataFactory.createRoomCreationVo;
import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.USER_ID;
import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.createUserEntityWithoutFriend;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
class RoomIT extends DatabaseRelatedTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RoomJpaRepository roomJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

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
    void createNewRoom() throws Exception {
        String tokenString = createTestTokenString();

        RoomCreationVo roomCreationVo = createRoomCreationVo();

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(roomCreationVo);

        mock.perform(post("/room")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status()
                        .isCreated());

        Optional<RoomEntity> roomEntityOptional = roomJpaRepository.findAll().stream().findFirst();

        assertThat(roomEntityOptional).isPresent();

        assertThat(roomEntityOptional.get().getId()).isNotNull();
        assertThat(roomEntityOptional.get().getMembers().iterator().next().getId()).isEqualTo(USER_ID);
    }

}
