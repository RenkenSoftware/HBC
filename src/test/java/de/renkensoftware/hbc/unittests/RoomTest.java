package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.room.core.model.Room;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RoomTest {

    @Test
    void RoomConstructor1() {
        Collection<UUID> memberIds = List.of(UUID.randomUUID());

        Room room = new Room(memberIds);

        assertThat(room.getId()).isNotNull();
        assertThat(room.getMemberIds()).hasSize(1);
    }

    @Test
    void RoomConstructor2() {
        Collection<UUID> memberIds = List.of(UUID.randomUUID());
        UUID id = UUID.randomUUID();

        Room room = new Room(id, memberIds);

        assertThat(room.getId()).isEqualTo(id);
        assertThat(room.getMemberIds()).hasSize(1);
    }

    @Test
    void addMemberId() {
        Room room = new Room(UUID.randomUUID(), Collections.emptyList());
        UUID memberId = UUID.randomUUID();

        room.addMemberId(memberId);

        assertThat(room.getMemberIds().iterator().next()).isEqualTo(memberId);
    }
}
