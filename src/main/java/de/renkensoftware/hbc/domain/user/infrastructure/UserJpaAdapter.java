package de.renkensoftware.hbc.domain.user.infrastructure;

import de.renkensoftware.hbc.domain.user.core.model.User;
import de.renkensoftware.hbc.domain.user.core.ports.UserOutgoingPort;
import de.renkensoftware.hbc.domain.user.infrastructure.mapper.UserEntityMapper;
import de.renkensoftware.hbc.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserJpaAdapter implements UserOutgoingPort {

    private final UserEntityMapper userEntityMapper;
    private final UserJpaRepository userJpaRepository;

    @Override
    public void save(final User user) {
        userJpaRepository.save(userEntityMapper.toEntity(user));
    }

    @Override
    public User findByEmail(final String email) {
        return userEntityMapper.toUser(userJpaRepository.findByEmail(email).orElseThrow(UserNotFoundException::new));
    }

    @Override
    public User findById(final UUID id) {
        return userEntityMapper.toUser(userJpaRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }
}
