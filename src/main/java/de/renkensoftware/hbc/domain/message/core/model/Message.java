package de.renkensoftware.hbc.domain.message.core.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Message {

    private final UUID id;

    private final UUID senderId;

    private final UUID roomId;

    private final String content;

    public Message(final UUID senderId, final UUID roomId, final String content) {
        this.id = UUID.randomUUID();
        this.senderId = senderId;
        this.roomId = roomId;
        this.content = content;
    }
}
