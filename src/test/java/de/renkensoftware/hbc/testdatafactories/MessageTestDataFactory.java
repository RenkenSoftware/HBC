package de.renkensoftware.hbc.testdatafactories;

import de.renkensoftware.hbc.domain.message.application.viewobjects.MessageCreationVo;
import de.renkensoftware.hbc.domain.message.application.viewobjects.MessagePresentationVo;
import de.renkensoftware.hbc.domain.message.core.model.Message;
import de.renkensoftware.hbc.domain.message.infrastructure.entity.MessageEntity;

import java.util.UUID;

import static de.renkensoftware.hbc.testdatafactories.RoomTestDataFactory.ROOM_ID;
import static de.renkensoftware.hbc.testdatafactories.RoomTestDataFactory.createRoomEntity;
import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.*;

public abstract class MessageTestDataFactory {

    public static UUID MESSAGE_ID = UUID.randomUUID();
    public static String CONTENT = "testcontent";

    public static MessageCreationVo createMessageCreationVo() {
        MessageCreationVo messageCreationVo = new MessageCreationVo();
        messageCreationVo.setRoomId(ROOM_ID);
        messageCreationVo.setContent(CONTENT);
        return messageCreationVo;
    }

    public static Message createMessageAtCreation() {
        return new Message(USER_ID, ROOM_ID, CONTENT);
    }

    public static Message createMessage() {
        return new Message(MESSAGE_ID, USER_ID, ROOM_ID, CONTENT);
    }

    public static MessageEntity createMessageEntity() {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setId(MESSAGE_ID);
        messageEntity.setSenderEntity(createUserEntityWithoutFriend());
        messageEntity.setRoomEntity(createRoomEntity());
        messageEntity.setContent(CONTENT);
        return messageEntity;
    }

    public static MessagePresentationVo createMessagePresentationVo() {
        MessagePresentationVo messagePresentationVo = new MessagePresentationVo();
        messagePresentationVo.setId(MESSAGE_ID);
        messagePresentationVo.setSenderName(NAME);
        messagePresentationVo.setContent(CONTENT);
        return messagePresentationVo;
    }
}
