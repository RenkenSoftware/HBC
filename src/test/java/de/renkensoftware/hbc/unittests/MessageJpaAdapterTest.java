package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.message.core.model.Message;
import de.renkensoftware.hbc.domain.message.infrastructure.MessageJpaAdapter;
import de.renkensoftware.hbc.domain.message.infrastructure.MessageJpaRepository;
import de.renkensoftware.hbc.domain.message.infrastructure.entity.MessageEntity;
import de.renkensoftware.hbc.domain.message.infrastructure.mapper.MessageEntityMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.renkensoftware.hbc.testdatafactories.MessageTestDataFactory.*;
import static de.renkensoftware.hbc.testdatafactories.RoomTestDataFactory.ROOM_ID;
import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MessageJpaAdapterTest {

    private final MessageEntityMapper messageEntityMapper = mock(MessageEntityMapper.class);
    private final MessageJpaRepository messageJpaRepository = mock(MessageJpaRepository.class);

    private final MessageJpaAdapter messageJpaAdapter =
            new MessageJpaAdapter(messageEntityMapper, messageJpaRepository);

    @Test
    void save() {
        Message message = createMessage();

        MessageEntity messageEntity = createMessageEntity();

        when(messageEntityMapper.toEntity(message)).thenReturn(messageEntity);

        messageJpaAdapter.save(message);

        verify(messageJpaRepository).save(messageEntity);
    }

    @Test
    void findAllByRoomId() {
        Message message = createMessage();
        MessageEntity messageEntity = createMessageEntity();

        when(messageEntityMapper.toMessage(messageEntity)).thenReturn(message);
        when(messageJpaRepository.findAllByRoomEntity_Id(ROOM_ID)).thenReturn(List.of(messageEntity));

        List<Message> messageList = messageJpaAdapter.findAllByRoomId(ROOM_ID);

        assertThat(messageList).hasSize(1);
        assertThat(messageList.get(0).getId()).isEqualTo(MESSAGE_ID);
        assertThat(messageList.get(0).getSenderId()).isEqualTo(USER_ID);
        assertThat(messageList.get(0).getRoomId()).isEqualTo(ROOM_ID);
        assertThat(messageList.get(0).getContent()).isEqualTo(CONTENT);
    }
}