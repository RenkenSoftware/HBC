package de.renkensoftware.hbc.domain.room.core.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Room {

    private final UUID id;

    private final Collection<UUID> memberIds;

    public Room(final Collection<UUID> memberIds) {
        this.id = UUID.randomUUID();
        this.memberIds = memberIds;
    }

    public void addMemberId(final UUID memberId) {
        this.memberIds.add(memberId);
    }
}
