package de.renkensoftware.hbc.domain.message.infrastructure;

import de.renkensoftware.hbc.domain.message.core.model.Message;
import de.renkensoftware.hbc.domain.message.core.ports.MessageOutgoingPort;
import de.renkensoftware.hbc.domain.message.infrastructure.mapper.MessageEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageJpaAdapter implements MessageOutgoingPort {

    private final MessageEntityMapper messageEntityMapper;
    private final MessageJpaRepository messageJpaRepository;

    @Override
    public void save(final Message message) {
        messageJpaRepository.save(messageEntityMapper.toEntity(message));
    }

    @Override
    public List<Message> findAllByRoomId(final UUID roomId) {
        return messageJpaRepository.findAllByRoomEntity_Id(roomId).stream()
                .map(messageEntityMapper::toMessage)
                .toList();
    }
}
