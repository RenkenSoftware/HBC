package de.renkensoftware.hbc.domain.message.core.ports;

import de.renkensoftware.hbc.domain.message.core.model.Message;
import org.springframework.stereotype.Service;

@Service
public interface MessageOutgoingPort {

    void save(final Message message);
}
