package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.room.core.model.Room;
import de.renkensoftware.hbc.domain.room.infrastructure.RoomJpaAdapter;
import de.renkensoftware.hbc.domain.room.infrastructure.RoomJpaRepository;
import de.renkensoftware.hbc.domain.room.infrastructure.entity.RoomEntity;
import de.renkensoftware.hbc.domain.room.infrastructure.mapper.RoomEntityMapper;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomJpaAdapterTest {

    private final RoomJpaRepository roomJpaRepository = mock(RoomJpaRepository.class);
    private final RoomEntityMapper roomEntityMapper = mock(RoomEntityMapper.class);

    private final RoomJpaAdapter roomJpaAdapter = new RoomJpaAdapter(roomJpaRepository, roomEntityMapper);

    @Test
    void save() {
        UUID id = UUID.randomUUID();

        Room room = new Room(id, Collections.emptyList());
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setId(id);
        roomEntity.setMembers(Collections.emptyList());

        when(roomEntityMapper.toEntity(room)).thenReturn(roomEntity);

        roomJpaAdapter.save(room);

        verify(roomJpaRepository).save(roomEntity);
    }
}