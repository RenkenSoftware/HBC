package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.room.core.RoomFacade;
import de.renkensoftware.hbc.domain.room.core.model.Room;
import de.renkensoftware.hbc.domain.room.core.ports.RoomOutgoingPort;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RoomFacadeTest {

    private final RoomOutgoingPort roomOutgoingPort = mock(RoomOutgoingPort.class);

    private final RoomFacade roomFacade = new RoomFacade(roomOutgoingPort);

    @Test
    void save() {
        Room room = new Room(List.of(UUID.randomUUID()));

        roomFacade.save(room);

        verify(roomOutgoingPort).save(room);
    }
}