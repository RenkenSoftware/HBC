package de.renkensoftware.hbc.domain.user.infrastructure;

import de.renkensoftware.hbc.domain.user.core.model.User;
import de.renkensoftware.hbc.domain.user.core.ports.UserOutgoingPort;
import de.renkensoftware.hbc.domain.user.infrastructure.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
    public Optional<User> findByEmail(final String email) {
        return userJpaRepository.findByEmail(email).map(userEntityMapper::toUser);
    }

    @Override
    public Optional<User> findById(final UUID id) {
        return userJpaRepository.findById(id).map(userEntityMapper::toUser);
    }
}
