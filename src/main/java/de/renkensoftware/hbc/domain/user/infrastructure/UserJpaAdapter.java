package de.renkensoftware.hbc.domain.user.infrastructure;

import de.renkensoftware.hbc.domain.user.core.model.User;
import de.renkensoftware.hbc.domain.user.core.ports.UserOutgoingPort;
import de.renkensoftware.hbc.domain.user.infrastructure.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserJpaAdapter implements UserOutgoingPort {

    private final UserEntityMapper userEntityMapper;
    private final UserJpaRepository userJpaRepository;

    @Override
    public void save(final User user) {
        userJpaRepository.save(userEntityMapper.toEntity(user));
    }
}
