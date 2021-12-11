package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.user.core.model.User;
import de.renkensoftware.hbc.domain.user.infrastructure.UserJpaRepository;
import de.renkensoftware.hbc.domain.user.infrastructure.entity.UserEntity;
import de.renkensoftware.hbc.domain.user.infrastructure.mapper.UserEntityMapper;
import org.junit.jupiter.api.Test;

import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserEntityMapperTest {

    private final UserJpaRepository userJpaRepository = mock(UserJpaRepository.class);

    private final UserEntityMapper userEntityMapper = new UserEntityMapper(userJpaRepository);

    @Test
    void toEntity() {
        User user = createUser();

        UserEntity userEntity = createUserEntity();

        when(userJpaRepository.getById(FRIEND_ID))
                .thenReturn(userEntity.getFriends().iterator().next());

        UserEntity resultEntity = userEntityMapper.toEntity(user);

        assertThat(resultEntity.getId()).isEqualTo(USER_ID);
        assertThat(resultEntity.getEmail()).isEqualTo(EMAIL);
        assertThat(resultEntity.getPassword()).isEqualTo(PASSWORD);
        assertThat(resultEntity.getName()).isEqualTo(NAME);
        assertThat(resultEntity.getFriends().iterator().next()
                .getFriends().iterator().next().getId()).isEqualTo(USER_ID);
    }

    @Test
    void toUser() {
        UserEntity userEntity = createUserEntity();

        User user = userEntityMapper.toUser(userEntity);

        assertThat(user.getId()).isEqualTo(USER_ID);
        assertThat(user.getEmail()).isEqualTo(EMAIL);
        assertThat(user.getPassword()).isEqualTo(PASSWORD);
        assertThat(user.getName()).isEqualTo(NAME);
        assertThat(user.getFriendIds().iterator().next()).isEqualTo(FRIEND_ID);
    }
}