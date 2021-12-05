package de.renkensoftware.hbc.domain.message.infrastructure.mapper;

import de.renkensoftware.hbc.domain.message.core.model.Message;
import de.renkensoftware.hbc.domain.message.infrastructure.entity.MessageEntity;
import de.renkensoftware.hbc.domain.room.infrastructure.RoomJpaRepository;
import de.renkensoftware.hbc.domain.user.infrastructure.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageEntityMapper {

    private final RoomJpaRepository roomJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public MessageEntity toEntity(final Message message) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setId(message.getId());
        messageEntity.setContent(message.getContent());
        messageEntity.setRoomEntity(roomJpaRepository.getById(message.getRoomId()));
        messageEntity.setSenderEntity(userJpaRepository.getById(message.getSenderId()));
        return messageEntity;
    }
}
