package de.renkensoftware.hbc.domain.message.infrastructure;

import de.renkensoftware.hbc.domain.message.core.model.Message;
import de.renkensoftware.hbc.domain.message.core.ports.MessageOutgoingPort;
import de.renkensoftware.hbc.domain.message.infrastructure.mapper.MessageEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageJpaAdapter implements MessageOutgoingPort {

    private final MessageEntityMapper messageEntityMapper;
    private final MessageJpaRepository messageJpaRepository;

    @Override
    public void save(final Message message) {
        messageJpaRepository.save(messageEntityMapper.toEntity(message));
    }
}
