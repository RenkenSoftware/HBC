package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.user.core.model.User;
import de.renkensoftware.hbc.domain.user.infrastructure.UserJpaRepository;
import de.renkensoftware.hbc.domain.user.infrastructure.entity.UserEntity;
import de.renkensoftware.hbc.domain.user.infrastructure.mapper.UserEntityMapper;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserEntityMapperTest {

    private final UserJpaRepository userJpaRepository = mock(UserJpaRepository.class);

    private final UserEntityMapper userEntityMapper = new UserEntityMapper(userJpaRepository);

    @Test
    void toEntity() {
        UUID id = UUID.randomUUID();
        UUID friendId = UUID.randomUUID();

        User user = new User(id, "email", "password", "name", Collections.emptyList());
        User friend = new User(friendId,
                "friendemail",
                "friendpassword",
                "friendname",
                Collections.emptyList());
        user.addFriendId(friendId);
        friend.addFriendId(id);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setEmail("email");
        userEntity.setPassword("password");
        userEntity.setName("name");

        UserEntity friendEntity = new UserEntity();
        friendEntity.setId(friendId);
        friendEntity.setEmail("friendemail");
        friendEntity.setPassword("friendpassword");
        friendEntity.setName("friendname");

        userEntity.setFriends(List.of(friendEntity));
        friendEntity.setFriends(List.of(userEntity));

        when(userJpaRepository.getById(friendId)).thenReturn(friendEntity);

        UserEntity resultEntity = userEntityMapper.toEntity(user);

        assertThat(resultEntity.getId()).isEqualTo(id);
        assertThat(resultEntity.getEmail()).isEqualTo("email");
        assertThat(resultEntity.getPassword()).isEqualTo("password");
        assertThat(resultEntity.getName()).isEqualTo("name");
        assertThat(resultEntity.getFriends().iterator().next().getFriends().iterator().next().getId()).isEqualTo(id);
    }

    @Test
    void toUser() {
        UUID friendId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        UserEntity friendEntity = new UserEntity();
        friendEntity.setId(friendId);
        friendEntity.setEmail("friendemail");
        friendEntity.setPassword("friendpassword");
        friendEntity.setName("friendname");

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setEmail("email");
        userEntity.setPassword("password");
        userEntity.setName("name");
        userEntity.setFriends(List.of(friendEntity));

        User user = userEntityMapper.toUser(userEntity);

        assertThat(user.getId()).isEqualTo(userId);
        assertThat(user.getEmail()).isEqualTo("email");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getName()).isEqualTo("name");
        assertThat(user.getFriendIds().iterator().next()).isEqualTo(friendId);
    }
}