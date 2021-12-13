package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.message.application.mapper.MessageVoMapper;
import de.renkensoftware.hbc.domain.message.application.viewobjects.MessageCreationVo;
import de.renkensoftware.hbc.domain.message.application.viewobjects.MessagePresentationVo;
import de.renkensoftware.hbc.domain.message.core.model.Message;
import de.renkensoftware.hbc.domain.user.core.model.User;
import de.renkensoftware.hbc.domain.user.core.ports.UserIncomingPort;
import de.renkensoftware.hbc.testdatafactories.MessageTestDataFactory;
import de.renkensoftware.hbc.testdatafactories.UserTestDataFactory;
import org.junit.jupiter.api.Test;

import static de.renkensoftware.hbc.testdatafactories.MessageTestDataFactory.*;
import static de.renkensoftware.hbc.testdatafactories.RoomTestDataFactory.ROOM_ID;
import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.NAME;
import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MessageVoMapperTest {

    private final UserIncomingPort userIncomingPort = mock(UserIncomingPort.class);
    private final MessageVoMapper messageVoMapper = new MessageVoMapper(userIncomingPort);

    @Test
    void toMessage() {
        MessageCreationVo messageCreationVo = createMessageCreationVo();
        messageCreationVo.setSenderId(USER_ID);

        Message message = messageVoMapper.toMessage(messageCreationVo);

        assertThat(message.getId()).isNotNull();
        assertThat(message.getSenderId()).isEqualTo(USER_ID);
        assertThat(message.getRoomId()).isEqualTo(ROOM_ID);
        assertThat(message.getContent()).isEqualTo(CONTENT);
    }

    @Test
    void toMessagePresentationVo() {
        User sender = UserTestDataFactory.createUserWithoutFriend();
        Message message = MessageTestDataFactory.createMessage();

        when(userIncomingPort.findById(USER_ID)).thenReturn(sender);

        MessagePresentationVo messagePresentationVo = messageVoMapper.toMessagePresentationVo(message);

        assertThat(messagePresentationVo.getId()).isEqualTo(MESSAGE_ID);
        assertThat(messagePresentationVo.getSenderName()).isEqualTo(NAME);
        assertThat(messagePresentationVo.getContent()).isEqualTo(CONTENT);
    }
}