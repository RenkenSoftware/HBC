package de.renkensoftware.hbc.domain.room.application.viewobjects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class RoomCreationVo {

    private final Collection<UUID> memberIds;
}
