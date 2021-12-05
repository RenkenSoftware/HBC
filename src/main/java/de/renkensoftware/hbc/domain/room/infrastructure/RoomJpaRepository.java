package de.renkensoftware.hbc.domain.room.infrastructure;

import de.renkensoftware.hbc.domain.room.infrastructure.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomJpaRepository extends JpaRepository<RoomEntity, UUID> {
}
