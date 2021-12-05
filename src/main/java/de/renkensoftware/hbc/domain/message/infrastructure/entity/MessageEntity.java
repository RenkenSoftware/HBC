package de.renkensoftware.hbc.domain.message.infrastructure.entity;

import de.renkensoftware.hbc.domain.room.infrastructure.entity.RoomEntity;
import de.renkensoftware.hbc.domain.user.infrastructure.entity.UserEntity;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Getter
@Setter
public class MessageEntity {

    @Id
    private UUID id;

    @ManyToOne
    private UserEntity senderEntity;

    @ManyToOne
    private RoomEntity roomEntity;

    private String content;
}
