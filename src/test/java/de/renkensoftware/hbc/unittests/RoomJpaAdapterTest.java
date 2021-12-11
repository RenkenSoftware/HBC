package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.room.core.model.Room;
import de.renkensoftware.hbc.domain.room.infrastructure.RoomJpaAdapter;
import de.renkensoftware.hbc.domain.room.infrastructure.RoomJpaRepository;
import de.renkensoftware.hbc.domain.room.infrastructure.entity.RoomEntity;
import de.renkensoftware.hbc.domain.room.infrastructure.mapper.RoomEntityMapper;
import org.junit.jupiter.api.Test;

import static de.renkensoftware.hbc.testdatafactories.RoomTestDataFactory.createRoom;
import static de.renkensoftware.hbc.testdatafactories.RoomTestDataFactory.createRoomEntity;
import static org.mockito.Mockito.*;

class RoomJpaAdapterTest {

    private final RoomJpaRepository roomJpaRepository = mock(RoomJpaRepository.class);
    private final RoomEntityMapper roomEntityMapper = mock(RoomEntityMapper.class);

    private final RoomJpaAdapter roomJpaAdapter = new RoomJpaAdapter(roomJpaRepository, roomEntityMapper);

    @Test
    void save() {
        Room room = createRoom();
        RoomEntity roomEntity = createRoomEntity();

        when(roomEntityMapper.toEntity(room)).thenReturn(roomEntity);

        roomJpaAdapter.save(room);

        verify(roomJpaRepository).save(roomEntity);
    }
}