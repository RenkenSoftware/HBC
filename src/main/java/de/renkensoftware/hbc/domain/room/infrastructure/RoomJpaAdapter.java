package de.renkensoftware.hbc.domain.room.infrastructure;

import de.renkensoftware.hbc.domain.room.core.model.Room;
import de.renkensoftware.hbc.domain.room.core.ports.RoomOutgoingPort;
import de.renkensoftware.hbc.domain.room.infrastructure.mapper.RoomEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomJpaAdapter implements RoomOutgoingPort {

    private final RoomJpaRepository roomJpaRepository;
    private final RoomEntityMapper roomEntityMapper;

    @Override
    public void save(final Room room) {
        roomJpaRepository.save(roomEntityMapper.toEntity(room));
    }
}
