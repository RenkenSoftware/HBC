package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.room.application.mapper.RoomVoMapper;
import de.renkensoftware.hbc.domain.room.application.viewobjects.RoomCreationVo;
import de.renkensoftware.hbc.domain.room.core.model.Room;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RoomVoMapperTest {

    private final RoomVoMapper roomVoMapper = new RoomVoMapper();

    @Test
    void toRoom() {
        Collection<UUID> memberIds = List.of(UUID.randomUUID());

        RoomCreationVo roomCreationVo = new RoomCreationVo();
        roomCreationVo.setMemberIds(memberIds);

        Room room = roomVoMapper.toRoom(roomCreationVo);

        assertThat(room.getId()).isNotNull();
        assertThat(room.getMemberIds()).hasSize(1);
    }
}