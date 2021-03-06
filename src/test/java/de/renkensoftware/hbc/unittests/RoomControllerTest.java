package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.room.application.RoomController;
import de.renkensoftware.hbc.domain.room.application.mapper.RoomVoMapper;
import de.renkensoftware.hbc.domain.room.application.viewobjects.RoomCreationVo;
import de.renkensoftware.hbc.domain.room.core.model.Room;
import de.renkensoftware.hbc.domain.room.core.ports.RoomIncomingPort;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static de.renkensoftware.hbc.testdatafactories.RoomTestDataFactory.createRoomAtCreation;
import static de.renkensoftware.hbc.testdatafactories.RoomTestDataFactory.createRoomCreationVo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RoomControllerTest {

    private final RoomIncomingPort roomIncomingPort = mock(RoomIncomingPort.class);
    private final RoomVoMapper roomVoMapper = mock(RoomVoMapper.class);

    private final RoomController roomController = new RoomController(roomIncomingPort, roomVoMapper);

    @Test
    void create() {
        RoomCreationVo roomCreationVo = createRoomCreationVo();

        Room room = createRoomAtCreation();

        when(roomVoMapper.toRoom(roomCreationVo)).thenReturn(room);

        ResponseEntity<String> response = roomController.create(roomCreationVo);

        verify(roomIncomingPort).save(room);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}