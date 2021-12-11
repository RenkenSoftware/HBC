package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.message.core.MessageFacade;
import de.renkensoftware.hbc.domain.message.core.model.Message;
import de.renkensoftware.hbc.domain.message.core.ports.MessageOutgoingPort;
import org.junit.jupiter.api.Test;

import static de.renkensoftware.hbc.testdatafactories.MessageTestDataFactory.createMessage;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MessageFacadeTest {

    private final MessageOutgoingPort messageOutgoingPort = mock(MessageOutgoingPort.class);

    private final MessageFacade messageFacade = new MessageFacade(messageOutgoingPort);

    @Test
    void save() {
        Message message = createMessage();

        messageFacade.save(message);

        verify(messageOutgoingPort).save(message);
    }
}