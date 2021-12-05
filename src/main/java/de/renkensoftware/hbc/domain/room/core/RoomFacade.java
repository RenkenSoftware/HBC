package de.renkensoftware.hbc.domain.room.core;

import de.renkensoftware.hbc.domain.room.core.model.Room;
import de.renkensoftware.hbc.domain.room.core.ports.RoomIncomingPort;
import de.renkensoftware.hbc.domain.room.core.ports.RoomOutgoingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomFacade implements RoomIncomingPort {

    private final RoomOutgoingPort roomOutgoingPort;

    @Override
    public void save(final Room room) {
        roomOutgoingPort.save(room);
    }
}
