package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.message.application.MessageController;
import de.renkensoftware.hbc.domain.message.application.mapper.MessageVoMapper;
import de.renkensoftware.hbc.domain.message.application.viewobjects.MessageCreationVo;
import de.renkensoftware.hbc.domain.message.core.model.Message;
import de.renkensoftware.hbc.domain.message.core.ports.MessageIncomingPort;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

import static de.renkensoftware.hbc.testdatafactories.MessageTestDataFactory.createMessageAtCreation;
import static de.renkensoftware.hbc.testdatafactories.MessageTestDataFactory.createMessageCreationVo;
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
}