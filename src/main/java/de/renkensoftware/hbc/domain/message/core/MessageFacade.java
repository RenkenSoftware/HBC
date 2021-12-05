package de.renkensoftware.hbc.domain.message.core;

import de.renkensoftware.hbc.domain.message.core.model.Message;
import de.renkensoftware.hbc.domain.message.core.ports.MessageIncomingPort;
import de.renkensoftware.hbc.domain.message.core.ports.MessageOutgoingPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessageFacade implements MessageIncomingPort {

    private final MessageOutgoingPort messageOutgoingPort;

    @Override
    public void save(final Message message) {
        messageOutgoingPort.save(message);
    }
}
