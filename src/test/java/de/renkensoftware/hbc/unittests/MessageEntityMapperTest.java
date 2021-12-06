package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.message.core.model.Message;
import de.renkensoftware.hbc.domain.message.infrastructure.entity.MessageEntity;
import de.renkensoftware.hbc.domain.message.infrastructure.mapper.MessageEntityMapper;
import de.renkensoftware.hbc.domain.room.infrastructure.RoomJpaRepository;
import de.renkensoftware.hbc.domain.room.infrastructure.entity.RoomEntity;
import de.renkensoftware.hbc.domain.user.infrastructure.UserJpaRepository;
import de.renkensoftware.hbc.domain.user.infrastructure.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MessageEntityMapperTest {

    private final RoomJpaRepository roomJpaRepository = mock(RoomJpaRepository.class);
    private final UserJpaRepository userJpaRepository = mock(UserJpaRepository.class);

    private final MessageEntityMapper messageEntityMapper = new MessageEntityMapper(roomJpaRepository, userJpaRepository);

    @Test
    void toEntity() {
        UUID messageId = UUID.randomUUID();
        UUID roomId = UUID.randomUUID();
        UUID senderId = UUID.randomUUID();

        Message message = new Message(messageId, senderId, roomId, "content");
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setId(roomId);
        roomEntity.setMembers(Collections.emptyList());
        UserEntity senderEntity = new UserEntity();
        senderEntity.setId(senderId);
        senderEntity.setEmail("email");
        senderEntity.setPassword("password");
        senderEntity.setName("name");
        senderEntity.setFriends(Collections.emptyList());

        when(roomJpaRepository.getById(roomId)).thenReturn(roomEntity);
        when(userJpaRepository.getById(senderId)).thenReturn(senderEntity);

        MessageEntity messageEntity = messageEntityMapper.toEntity(message);

        assertThat(messageEntity.getId()).isEqualTo(messageId);
        assertThat(messageEntity.getRoomEntity().getId()).isEqualTo(roomId);
        assertThat(messageEntity.getSenderEntity().getId()).isEqualTo(senderId);
        assertThat(messageEntity.getContent()).isEqualTo("content");
    }
}