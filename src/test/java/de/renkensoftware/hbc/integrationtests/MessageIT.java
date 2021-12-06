package de.renkensoftware.hbc.integrationtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.renkensoftware.hbc.domain.message.application.viewobjects.MessageCreationVo;
import de.renkensoftware.hbc.domain.message.infrastructure.MessageJpaRepository;
import de.renkensoftware.hbc.domain.message.infrastructure.entity.MessageEntity;
import de.renkensoftware.hbc.domain.room.application.viewobjects.RoomCreationVo;
import de.renkensoftware.hbc.domain.room.infrastructure.RoomJpaRepository;
import de.renkensoftware.hbc.domain.room.infrastructure.entity.RoomEntity;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@WebAppConfiguration
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
                .build();
    }

    @Test
    void createNewMessage() throws Exception {
        UUID senderId = UUID.randomUUID();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(senderId);
        userEntity.setEmail("email");
        userEntity.setPassword("password");
        userEntity.setName("name");
        userEntity.setFriends(Collections.emptyList());

        userJpaRepository.save(userEntity);

        UUID roomId = UUID.randomUUID();
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setId(roomId);
        roomEntity.setMembers(List.of(userEntity));

        roomJpaRepository.save(roomEntity);

        MessageCreationVo messageCreationVo = new MessageCreationVo();
        messageCreationVo.setSenderId(senderId);
        messageCreationVo.setRoomId(roomId);
        messageCreationVo.setContent("content");

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(messageCreationVo);

        mock.perform(post("/message").contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status()
                        .isCreated());

        Optional<MessageEntity> messageEntityOptional = messageJpaRepository.findAll().stream().findFirst();

        assertThat(messageEntityOptional).isPresent();

        assertThat(messageEntityOptional.get().getId()).isNotNull();
        assertThat(messageEntityOptional.get().getSenderEntity().getId()).isEqualTo(senderId);
        assertThat(messageEntityOptional.get().getRoomEntity().getId()).isEqualTo(roomId);
        assertThat(messageEntityOptional.get().getContent()).isEqualTo("content");
    }
}
