package de.renkensoftware.hbc.domain.room.core.ports;

import de.renkensoftware.hbc.domain.room.core.model.Room;
import org.springframework.stereotype.Service;

@Service
public interface RoomIncomingPort {

    void save(final Room room);
}
