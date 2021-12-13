package de.renkensoftware.hbc.domain.message.application.mapper;

import de.renkensoftware.hbc.domain.message.application.viewobjects.MessageCreationVo;
import de.renkensoftware.hbc.domain.message.application.viewobjects.MessagePresentationVo;
import de.renkensoftware.hbc.domain.message.core.model.Message;
import de.renkensoftware.hbc.domain.user.core.ports.UserIncomingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageVoMapper {

    private final UserIncomingPort userIncomingPort;

    public Message toMessage(final MessageCreationVo messageCreationVo) {
        return new Message(messageCreationVo.getSenderId(),
                messageCreationVo.getRoomId(),
                messageCreationVo.getContent());
    }

    public MessagePresentationVo toMessagePresentationVo(final Message message) {
        MessagePresentationVo messagePresentationVo = new MessagePresentationVo();
        messagePresentationVo.setId(message.getId());
        messagePresentationVo.setSenderName(userIncomingPort.findById(message.getSenderId()).getName());
        messagePresentationVo.setContent(message.getContent());
        return messagePresentationVo;
    }
}
