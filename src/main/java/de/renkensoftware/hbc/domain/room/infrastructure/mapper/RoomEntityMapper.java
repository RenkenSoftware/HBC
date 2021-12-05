package de.renkensoftware.hbc.domain.room.infrastructure.mapper;

import de.renkensoftware.hbc.domain.room.core.model.Room;
import de.renkensoftware.hbc.domain.room.infrastructure.entity.RoomEntity;
import de.renkensoftware.hbc.domain.user.infrastructure.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomEntityMapper {

    private final UserJpaRepository userJpaRepository;

    public RoomEntity toEntity(final Room room) {
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setId(room.getId());
        roomEntity.setMembers(room.getMemberIds().stream().map(userJpaRepository::getById).toList());
        return roomEntity;
    }
}
