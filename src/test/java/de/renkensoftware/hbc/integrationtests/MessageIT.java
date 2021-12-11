package de.renkensoftware.hbc.integrationtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.renkensoftware.hbc.domain.authentication.application.viewobjects.AuthenticationVo;
import de.renkensoftware.hbc.domain.authentication.application.viewobjects.TokenVo;
import de.renkensoftware.hbc.domain.message.application.viewobjects.MessageCreationVo;
import de.renkensoftware.hbc.domain.message.infrastructure.MessageJpaRepository;
import de.renkensoftware.hbc.domain.message.infrastructure.entity.MessageEntity;
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
import static de.renkensoftware.hbc.testdatafactories.MessageTestDataFactory.CONTENT;
import static de.renkensoftware.hbc.testdatafactories.MessageTestDataFactory.createMessageCreationVo;
import static de.renkensoftware.hbc.testdatafactories.RoomTestDataFactory.ROOM_ID;
import static de.renkensoftware.hbc.testdatafactories.RoomTestDataFactory.createRoomEntity;
import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.EMAIL;
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
class MessageIT extends DatabaseRelatedTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private RoomJpaRepository roomJpaRepository;

    @Autowired
    private MessageJpaRepository messageJpaRepository;

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
    void createNewMessage() throws Exception {
        String tokenString = createTestTokenString();

        RoomEntity roomEntity = createRoomEntity();

        roomJpaRepository.save(roomEntity);

        MessageCreationVo messageCreationVo = createMessageCreationVo();

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(messageCreationVo);

        mock.perform(post("/message")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status()
                        .isCreated());

        Optional<MessageEntity> messageEntityOptional = messageJpaRepository.findAll().stream().findFirst();

        assertThat(messageEntityOptional).isPresent();

        assertThat(messageEntityOptional.get().getId()).isNotNull();
        assertThat(messageEntityOptional.get().getSenderEntity().getEmail()).isEqualTo(EMAIL);
        assertThat(messageEntityOptional.get().getRoomEntity().getId()).isEqualTo(ROOM_ID);
        assertThat(messageEntityOptional.get().getContent()).isEqualTo(CONTENT);
    }
}
