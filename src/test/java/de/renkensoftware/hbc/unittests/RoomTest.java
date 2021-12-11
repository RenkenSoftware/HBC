package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.room.core.model.Room;
import org.junit.jupiter.api.Test;

import java.util.*;

import static de.renkensoftware.hbc.testdatafactories.RoomTestDataFactory.ROOM_ID;
import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;

class RoomTest {

    @Test
    void RoomConstructor() {
        Collection<UUID> memberIds = List.of(USER_ID);

        Room room = new Room(memberIds);

        assertThat(room.getId()).isNotNull();
        assertThat(room.getMemberIds()).hasSize(1);
    }

    @Test
    void addMemberId() {
        Room room = new Room(ROOM_ID, new ArrayList<>());

        room.addMemberId(USER_ID);

        assertThat(room.getMemberIds().iterator().next()).isEqualTo(USER_ID);
    }
}
