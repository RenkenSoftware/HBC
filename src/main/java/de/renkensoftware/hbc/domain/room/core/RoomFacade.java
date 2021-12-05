package de.renkensoftware.hbc.domain.room.core;

import de.renkensoftware.hbc.domain.room.core.model.Room;
import de.renkensoftware.hbc.domain.room.core.ports.RoomIncomingPort;
import de.renkensoftware.hbc.domain.room.core.ports.RoomOutgoingPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoomFacade implements RoomIncomingPort {

    private final RoomOutgoingPort roomOutgoingPort;

    @Override
    public void save(Room room) {
        roomOutgoingPort.save(room);
    }
}
