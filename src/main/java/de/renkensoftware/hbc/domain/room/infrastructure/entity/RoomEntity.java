package de.renkensoftware.hbc.domain.room.infrastructure.entity;

import de.renkensoftware.hbc.domain.user.infrastructure.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;
import java.util.UUID;

@Entity
@Getter
@Setter
public class RoomEntity {

    @Id
    private UUID id;

    @ManyToMany
    private Collection<UserEntity> members;
}
