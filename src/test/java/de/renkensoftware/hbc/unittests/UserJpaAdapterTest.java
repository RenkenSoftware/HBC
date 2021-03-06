package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.user.core.model.User;
import de.renkensoftware.hbc.domain.user.infrastructure.UserJpaAdapter;
import de.renkensoftware.hbc.domain.user.infrastructure.UserJpaRepository;
import de.renkensoftware.hbc.domain.user.infrastructure.entity.UserEntity;
import de.renkensoftware.hbc.domain.user.infrastructure.mapper.UserEntityMapper;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserJpaAdapterTest {

    private final UserEntityMapper userEntityMapper = mock(UserEntityMapper.class);
    private final UserJpaRepository userJpaRepository = mock(UserJpaRepository.class);

    private final UserJpaAdapter userJpaAdapter = new UserJpaAdapter(userEntityMapper, userJpaRepository);

    @Test
    void save() {
        User user = createUserWithoutFriend();

        UserEntity userEntity = createUserEntityWithoutFriend();

        when(userEntityMapper.toEntity(user)).thenReturn(userEntity);

        userJpaAdapter.save(user);

        verify(userJpaRepository).save(userEntity);
    }

    @Test
    void findByEmail() {
        UserEntity userEntity = createUserEntity();

        User user = createUser();

        when(userJpaRepository.findByEmail(EMAIL)).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toUser(userEntity)).thenReturn(user);

        assertThat(userJpaAdapter.findByEmail(EMAIL).orElse(null)).isEqualTo(user);
    }

    @Test
    void findById() {
        UserEntity userEntity = createUserEntity();

        User user = createUser();

        when(userJpaRepository.findById(USER_ID)).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toUser(userEntity)).thenReturn(user);

        assertThat(userJpaAdapter.findById(USER_ID).orElse(null)).isEqualTo(user);
    }
}
