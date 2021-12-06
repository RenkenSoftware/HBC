package de.renkensoftware.hbc.domain.room.application.viewobjects;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
public class RoomCreationVo {

    private Collection<UUID> memberIds;
}
