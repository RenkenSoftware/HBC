package de.renkensoftware.hbc.domain.room.application.mapper;

import de.renkensoftware.hbc.domain.room.application.viewobjects.RoomCreationVo;
import de.renkensoftware.hbc.domain.room.core.model.Room;
import org.springframework.stereotype.Service;

@Service
public class RoomVoMapper {

    public Room toRoom(RoomCreationVo roomCreationVo) {
        return new Room(roomCreationVo.getMemberIds());
    }
}
