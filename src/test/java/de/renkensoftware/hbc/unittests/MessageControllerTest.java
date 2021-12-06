package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.message.application.MessageController;
import de.renkensoftware.hbc.domain.message.application.mapper.MessageVoMapper;
import de.renkensoftware.hbc.domain.message.application.viewobjects.MessageCreationVo;
import de.renkensoftware.hbc.domain.message.core.model.Message;
import de.renkensoftware.hbc.domain.message.core.ports.MessageIncomingPort;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MessageControllerTest {

    private final MessageIncomingPort messageIncomingPort = mock(MessageIncomingPort.class);
    private final MessageVoMapper messageVoMapper = mock(MessageVoMapper.class);

    private final MessageController messageController = new MessageController(messageIncomingPort, messageVoMapper);

    @Test
    void create() {
        UUID senderId = UUID.randomUUID();
        UUID roomId = UUID.randomUUID();

        MessageCreationVo messageCreationVo = new MessageCreationVo();
        messageCreationVo.setSenderId(senderId);
        messageCreationVo.setRoomId(roomId);
        messageCreationVo.setContent("content");

        Message message = new Message(senderId, roomId, "content");

        when(messageVoMapper.toMessage(messageCreationVo)).thenReturn(message);

        ResponseEntity<String> response = messageController.create(messageCreationVo);

        verify(messageIncomingPort).save(message);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}