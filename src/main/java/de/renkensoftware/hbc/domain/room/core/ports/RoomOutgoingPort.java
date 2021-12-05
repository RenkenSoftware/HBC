package de.renkensoftware.hbc.domain.room.core.ports;

import de.renkensoftware.hbc.domain.room.core.model.Room;

public interface RoomOutgoingPort {

    void save(final Room room);
}
