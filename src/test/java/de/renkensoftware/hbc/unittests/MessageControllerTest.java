package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.message.application.MessageController;
import de.renkensoftware.hbc.domain.message.application.mapper.MessageVoMapper;
import de.renkensoftware.hbc.domain.message.application.viewobjects.MessageCreationVo;
import de.renkensoftware.hbc.domain.message.application.viewobjects.MessagePresentationVo;
import de.renkensoftware.hbc.domain.message.core.model.Message;
import de.renkensoftware.hbc.domain.message.core.ports.MessageIncomingPort;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

import static de.renkensoftware.hbc.testdatafactories.MessageTestDataFactory.*;
import static de.renkensoftware.hbc.testdatafactories.RoomTestDataFactory.ROOM_ID;
import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MessageControllerTest {

    private final MessageIncomingPort messageIncomingPort = mock(MessageIncomingPort.class);
    private final MessageVoMapper messageVoMapper = mock(MessageVoMapper.class);
    private final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

    private final MessageController messageController =
            new MessageController(messageIncomingPort, messageVoMapper, httpServletRequest);

    @Test
    void create() {
        MessageCreationVo messageCreationVo = createMessageCreationVo();

        Message message = createMessageAtCreation();

        when(httpServletRequest.getRemoteUser()).thenReturn(message.getSenderId().toString());
        when(messageVoMapper.toMessage(messageCreationVo)).thenReturn(message);

        ResponseEntity<String> response = messageController.create(messageCreationVo);

        assertThat(messageCreationVo.getSenderId()).isEqualTo(USER_ID);
        verify(messageIncomingPort).save(message);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void getMessagesByRoomId() {
        Message message = createMessage();
        MessagePresentationVo messagePresentationVo = createMessagePresentationVo();

        when(messageIncomingPort.findAllByRoomId(ROOM_ID)).thenReturn(List.of(message));
        when(messageVoMapper.toMessagePresentationVo(message)).thenReturn(messagePresentationVo);

        ResponseEntity<List<MessagePresentationVo>> response = messageController.getMessagesByRoomId(ROOM_ID);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(Objects.requireNonNull(response.getBody()).iterator().next()).isEqualTo(messagePresentationVo);
    }
}