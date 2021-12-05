package de.renkensoftware.hbc.domain.message.core.ports;

import de.renkensoftware.hbc.domain.message.core.model.Message;

public interface MessageOutgoingPort {

    void save(final Message message);
}
