package de.renkensoftware.hbc.domain.message.core.ports;

import de.renkensoftware.hbc.domain.message.core.model.Message;

import java.util.List;
import java.util.UUID;

public interface MessageOutgoingPort {

    void save(final Message message);

    List<Message> findAllByRoomId(final UUID roomId);
}
