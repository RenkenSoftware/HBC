package de.renkensoftware.hbc.domain.user.infrastructure.mapper;

import de.renkensoftware.hbc.domain.user.core.model.User;
import de.renkensoftware.hbc.domain.user.infrastructure.UserJpaRepository;
import de.renkensoftware.hbc.domain.user.infrastructure.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEntityMapper {

    private final UserJpaRepository userJpaRepository;

    public UserEntity toEntity(final User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setName(user.getName());

        userEntity.setFriends(user.getFriendIds()
                .stream()
                .map(userJpaRepository::getById)
                .toList());

        return userEntity;
    }
}
