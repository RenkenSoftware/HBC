package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.message.application.mapper.MessageVoMapper;
import de.renkensoftware.hbc.domain.message.application.viewobjects.MessageCreationVo;
import de.renkensoftware.hbc.domain.message.core.model.Message;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MessageVoMapperTest {

    private final MessageVoMapper messageVoMapper = new MessageVoMapper();

    @Test
    void toMessage() {
        UUID senderId = UUID.randomUUID();
        UUID roomId = UUID.randomUUID();

        MessageCreationVo messageCreationVo = new MessageCreationVo(senderId, roomId, "content");

        Message message = messageVoMapper.toMessage(messageCreationVo);

        assertThat(message.getId()).isNotNull();
        assertThat(message.getSenderId()).isEqualTo(senderId);
        assertThat(message.getRoomId()).isEqualTo(roomId);
    }
}