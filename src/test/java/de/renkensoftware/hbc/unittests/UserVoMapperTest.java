package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.user.application.mapper.UserVoMapper;
import de.renkensoftware.hbc.domain.user.application.viewobjects.UserCreationVo;
import de.renkensoftware.hbc.domain.user.application.viewobjects.UserIdVo;
import de.renkensoftware.hbc.domain.user.core.model.User;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserVoMapperTest {

    private final UserVoMapper userVoMapper = new UserVoMapper();

    @Test
    void toUser() {
        UserCreationVo userCreationVo = new UserCreationVo();
        userCreationVo.setEmail("email");
        userCreationVo.setPassword("password");

        User user = userVoMapper.toUser(userCreationVo);

        assertThat(user.getId()).isNotNull();
        assertThat(user.getEmail()).isEqualTo("email");
        assertThat(user.getPassword()).isEqualTo("password");
    }

    @Test
    void toIdVo() {
        User user = new User(UUID.randomUUID(),
                "email",
                "password",
                "name",
                Collections.emptyList());

        UserIdVo userIdVo = userVoMapper.toIdVo(user);

        assertThat(userIdVo.getId()).isEqualTo(user.getId());
    }
}