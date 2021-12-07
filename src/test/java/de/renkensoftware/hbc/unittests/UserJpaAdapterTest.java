package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.user.core.model.User;
import de.renkensoftware.hbc.domain.user.infrastructure.UserJpaAdapter;
import de.renkensoftware.hbc.domain.user.infrastructure.UserJpaRepository;
import de.renkensoftware.hbc.domain.user.infrastructure.entity.UserEntity;
import de.renkensoftware.hbc.domain.user.infrastructure.mapper.UserEntityMapper;
import de.renkensoftware.hbc.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserJpaAdapterTest {

    private final UserEntityMapper userEntityMapper = mock(UserEntityMapper.class);
    private final UserJpaRepository userJpaRepository = mock(UserJpaRepository.class);

    private final UserJpaAdapter userJpaAdapter = new UserJpaAdapter(userEntityMapper, userJpaRepository);

    @Test
    void save() {
        UUID id = UUID.randomUUID();
        User user = new User(id,
                "email",
                "password",
                "name",
                Collections.emptyList());

        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setName("name");
        userEntity.setPassword("password");
        userEntity.setEmail("email");
        userEntity.setFriends(Collections.emptyList());

        when(userEntityMapper.toEntity(user)).thenReturn(userEntity);

        userJpaAdapter.save(user);

        verify(userJpaRepository).save(userEntity);
    }

    @Test
    void findByEmail() {
        String email = "email";
        UUID id = UUID.randomUUID();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setEmail(email);
        userEntity.setPassword("password");
        userEntity.setName("name");

        User user = new User(id, email, "password", "name", Collections.emptyList());

        when(userJpaRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toUser(userEntity)).thenReturn(user);

        assertThat(userJpaAdapter.findByEmail(email)).isEqualTo(user);
    }

    @Test
    void findByEmailWithoutResult() {
        String email = "email";

        when(userJpaRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userJpaAdapter.findByEmail(email));
    }
}