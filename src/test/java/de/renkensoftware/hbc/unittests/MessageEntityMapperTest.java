package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.message.core.model.Message;
import de.renkensoftware.hbc.domain.message.infrastructure.entity.MessageEntity;
import de.renkensoftware.hbc.domain.message.infrastructure.mapper.MessageEntityMapper;
import de.renkensoftware.hbc.domain.room.infrastructure.RoomJpaRepository;
import de.renkensoftware.hbc.domain.room.infrastructure.entity.RoomEntity;
import de.renkensoftware.hbc.domain.user.infrastructure.UserJpaRepository;
import de.renkensoftware.hbc.domain.user.infrastructure.entity.UserEntity;
import org.junit.jupiter.api.Test;

import static de.renkensoftware.hbc.testdatafactories.MessageTestDataFactory.*;
import static de.renkensoftware.hbc.testdatafactories.RoomTestDataFactory.ROOM_ID;
import static de.renkensoftware.hbc.testdatafactories.RoomTestDataFactory.createRoomEntity;
import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.USER_ID;
import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.createUserEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MessageEntityMapperTest {

    private final RoomJpaRepository roomJpaRepository = mock(RoomJpaRepository.class);
    private final UserJpaRepository userJpaRepository = mock(UserJpaRepository.class);

    private final MessageEntityMapper messageEntityMapper = new MessageEntityMapper(roomJpaRepository, userJpaRepository);

    @Test
    void toEntity() {
        Message message = createMessage();

        RoomEntity roomEntity = createRoomEntity();

        UserEntity senderEntity = createUserEntity();

        when(roomJpaRepository.getById(ROOM_ID)).thenReturn(roomEntity);
        when(userJpaRepository.getById(USER_ID)).thenReturn(senderEntity);

        MessageEntity messageEntity = messageEntityMapper.toEntity(message);

        assertThat(messageEntity.getId()).isEqualTo(MESSAGE_ID);
        assertThat(messageEntity.getRoomEntity().getId()).isEqualTo(ROOM_ID);
        assertThat(messageEntity.getSenderEntity().getId()).isEqualTo(USER_ID);
        assertThat(messageEntity.getContent()).isEqualTo(CONTENT);
    }
}