package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.room.application.mapper.RoomVoMapper;
import de.renkensoftware.hbc.domain.room.application.viewobjects.RoomCreationVo;
import de.renkensoftware.hbc.domain.room.core.model.Room;
import org.junit.jupiter.api.Test;

import static de.renkensoftware.hbc.testdatafactories.RoomTestDataFactory.createRoomCreationVo;
import static org.assertj.core.api.Assertions.assertThat;

class RoomVoMapperTest {

    private final RoomVoMapper roomVoMapper = new RoomVoMapper();

    @Test
    void toRoom() {
        RoomCreationVo roomCreationVo = createRoomCreationVo();

        Room room = roomVoMapper.toRoom(roomCreationVo);

        assertThat(room.getId()).isNotNull();
        assertThat(room.getMemberIds()).hasSize(1);
    }
}