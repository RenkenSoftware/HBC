package de.renkensoftware.hbc.domain.message.infrastructure;

import de.renkensoftware.hbc.domain.message.infrastructure.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageJpaRepository extends JpaRepository<MessageEntity, UUID> {

    List<MessageEntity> findAllByRoomEntity_Id(final UUID roomId);
}
