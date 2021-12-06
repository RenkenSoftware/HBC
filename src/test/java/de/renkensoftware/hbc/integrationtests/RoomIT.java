package de.renkensoftware.hbc.integrationtests;

import com.fasterxml.jackson.databind.ObjectMapper;
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
                .build();
    }

    @Test
    void createNewRoom() throws Exception {
        UUID memberId = UUID.randomUUID();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(memberId);
        userEntity.setEmail("email");
        userEntity.setPassword("password");
        userEntity.setName("name");
        userEntity.setFriends(Collections.emptyList());

        userJpaRepository.save(userEntity);

        RoomCreationVo roomCreationVo = new RoomCreationVo();
        roomCreationVo.setMemberIds(List.of(memberId));

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(roomCreationVo);

        mock.perform(post("/room").contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status()
                        .isCreated());

        Optional<RoomEntity> roomEntityOptional = roomJpaRepository.findAll().stream().findFirst();

        assertThat(roomEntityOptional).isPresent();

        assertThat(roomEntityOptional.get().getId()).isNotNull();
        assertThat(roomEntityOptional.get().getMembers().iterator().next().getId()).isEqualTo(memberId);
    }

}
