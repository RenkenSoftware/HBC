package de.renkensoftware.hbc.domain.room.core.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Getter
public class Room {

    private final UUID id;

    private final Collection<UUID> memberIds = new ArrayList<>();

    public Room(final Collection<UUID> memberIds) {
        this.id = UUID.randomUUID();
        this.memberIds.addAll(memberIds);
    }

    public Room(final UUID id, final Collection<UUID> memberIds) {
        this.id = id;
        this.memberIds.addAll(memberIds);
    }

    public void addMemberId(final UUID memberId) {
        this.memberIds.add(memberId);
    }
}
