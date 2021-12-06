package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.message.core.model.Message;
import de.renkensoftware.hbc.domain.message.infrastructure.MessageJpaAdapter;
import de.renkensoftware.hbc.domain.message.infrastructure.MessageJpaRepository;
import de.renkensoftware.hbc.domain.message.infrastructure.entity.MessageEntity;
import de.renkensoftware.hbc.domain.message.infrastructure.mapper.MessageEntityMapper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageJpaAdapterTest {

    private final MessageEntityMapper messageEntityMapper = mock(MessageEntityMapper.class);
    private final MessageJpaRepository messageJpaRepository = mock(MessageJpaRepository.class);

    private final MessageJpaAdapter messageJpaAdapter =
            new MessageJpaAdapter(messageEntityMapper, messageJpaRepository);

    @Test
    void save() {
        UUID messageId = UUID.randomUUID();
        UUID roomId = UUID.randomUUID();
        UUID senderId = UUID.randomUUID();

        Message message = new Message(messageId, senderId, roomId, "content");
        MessageEntity messageEntity = new MessageEntity();

        when(messageEntityMapper.toEntity(message)).thenReturn(messageEntity);

        messageJpaAdapter.save(message);

        verify(messageJpaRepository).save(messageEntity);
    }
}