package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.message.core.model.Message;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MessageTest {

    @Test
    void MessageConstructor() {
        UUID senderId = UUID.randomUUID();
        UUID roomId = UUID.randomUUID();

        Message message = new Message(senderId, roomId, "content");

        assertThat(message.getId()).isNotNull();
        assertThat(message.getSenderId()).isEqualTo(senderId);
        assertThat(message.getRoomId()).isEqualTo(roomId);
        assertThat(message.getContent()).isEqualTo("content");
    }

}