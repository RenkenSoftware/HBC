package de.renkensoftware.hbc.domain.message.application.mapper;

import de.renkensoftware.hbc.domain.message.application.viewobjects.MessageCreationVo;
import de.renkensoftware.hbc.domain.message.core.model.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageVoMapper {

    public Message toMessage(MessageCreationVo messageCreationVo) {
        return new Message(messageCreationVo.getSenderId(),
                messageCreationVo.getRoomId(),
                messageCreationVo.getContent());
    }
}
