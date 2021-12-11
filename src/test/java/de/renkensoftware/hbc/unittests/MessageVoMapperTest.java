package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.message.application.mapper.MessageVoMapper;
import de.renkensoftware.hbc.domain.message.application.viewobjects.MessageCreationVo;
import de.renkensoftware.hbc.domain.message.core.model.Message;
import org.junit.jupiter.api.Test;

import static de.renkensoftware.hbc.testdatafactories.MessageTestDataFactory.CONTENT;
import static de.renkensoftware.hbc.testdatafactories.MessageTestDataFactory.createMessageCreationVo;
import static de.renkensoftware.hbc.testdatafactories.RoomTestDataFactory.ROOM_ID;
import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;

class MessageVoMapperTest {

    private final MessageVoMapper messageVoMapper = new MessageVoMapper();

    @Test
    void toMessage() {
        MessageCreationVo messageCreationVo = createMessageCreationVo();

        Message message = messageVoMapper.toMessage(messageCreationVo);

        assertThat(message.getId()).isNotNull();
        assertThat(message.getSenderId()).isEqualTo(USER_ID);
        assertThat(message.getRoomId()).isEqualTo(ROOM_ID);
        assertThat(message.getContent()).isEqualTo(CONTENT);
    }
}