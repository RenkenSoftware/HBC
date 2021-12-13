package de.renkensoftware.hbc.testdatafactories;

import de.renkensoftware.hbc.domain.room.application.viewobjects.RoomCreationVo;
import de.renkensoftware.hbc.domain.room.core.model.Room;
import de.renkensoftware.hbc.domain.room.infrastructure.entity.RoomEntity;

import java.util.List;
import java.util.UUID;

import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.USER_ID;
import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.createUserEntityWithoutFriend;

public abstract class RoomTestDataFactory {

    public static UUID ROOM_ID = UUID.randomUUID();

    public static RoomCreationVo createRoomCreationVo() {
        RoomCreationVo roomCreationVo = new RoomCreationVo();
        roomCreationVo.setMemberIds(List.of(USER_ID));
        return roomCreationVo;
    }

    public static Room createRoomAtCreation() {
        return new Room(List.of(USER_ID));
    }

    public static Room createRoom() {
        return new Room(ROOM_ID, List.of(USER_ID));
    }

    public static RoomEntity createRoomEntity() {
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setId(ROOM_ID);
        roomEntity.setMembers(List.of(createUserEntityWithoutFriend()));
        return roomEntity;
    }
}
