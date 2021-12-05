package de.renkensoftware.hbc.domain.room.core.ports;

import de.renkensoftware.hbc.domain.room.core.model.Room;

public interface RoomIncomingPort {

    void save(final Room room);
}
