package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.message.core.MessageFacade;
import de.renkensoftware.hbc.domain.message.core.model.Message;
import de.renkensoftware.hbc.domain.message.core.ports.MessageOutgoingPort;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MessageFacadeTest {

    private final MessageOutgoingPort messageOutgoingPort = mock(MessageOutgoingPort.class);

    private final MessageFacade messageFacade = new MessageFacade(messageOutgoingPort);

    @Test
    void save() {
        Message message = new Message(UUID.randomUUID(), UUID.randomUUID(), "content");

        messageFacade.save(message);

        verify(messageOutgoingPort).save(message);
    }
}