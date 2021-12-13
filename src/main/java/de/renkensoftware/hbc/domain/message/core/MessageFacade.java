package de.renkensoftware.hbc.domain.message.core;

import de.renkensoftware.hbc.domain.message.core.model.Message;
import de.renkensoftware.hbc.domain.message.core.ports.MessageIncomingPort;
import de.renkensoftware.hbc.domain.message.core.ports.MessageOutgoingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageFacade implements MessageIncomingPort {

    private final MessageOutgoingPort messageOutgoingPort;

    @Override
    public void save(final Message message) {
        messageOutgoingPort.save(message);
    }

    @Override
    public List<Message> findAllByRoomId(final UUID roomId) {
        return messageOutgoingPort.findAllByRoomId(roomId);
    }
}
