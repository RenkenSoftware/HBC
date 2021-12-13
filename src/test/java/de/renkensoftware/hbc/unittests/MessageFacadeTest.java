package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.message.core.MessageFacade;
import de.renkensoftware.hbc.domain.message.core.model.Message;
import de.renkensoftware.hbc.domain.message.core.ports.MessageOutgoingPort;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.renkensoftware.hbc.testdatafactories.MessageTestDataFactory.createMessage;
import static de.renkensoftware.hbc.testdatafactories.RoomTestDataFactory.ROOM_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MessageFacadeTest {

    private final MessageOutgoingPort messageOutgoingPort = mock(MessageOutgoingPort.class);

    private final MessageFacade messageFacade = new MessageFacade(messageOutgoingPort);

    @Test
    void save() {
        Message message = createMessage();

        messageFacade.save(message);

        verify(messageOutgoingPort).save(message);
    }

    @Test
    void findAllByRoomId() {
        Message message = createMessage();

        when(messageOutgoingPort.findAllByRoomId(ROOM_ID)).thenReturn(List.of(message));

        List<Message> messageList = messageFacade.findAllByRoomId(ROOM_ID);

        assertThat(messageList).hasSize(1);
        assertThat(messageList.get(0)).isEqualTo(message);
    }
}