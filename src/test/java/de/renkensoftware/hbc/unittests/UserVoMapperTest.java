package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.user.application.mapper.UserVoMapper;
import de.renkensoftware.hbc.domain.user.application.viewobjects.UserCreationVo;
import de.renkensoftware.hbc.domain.user.core.model.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserVoMapperTest {

    private final UserVoMapper userVoMapper = new UserVoMapper();

    @Test
    void toUser() {
        UserCreationVo userCreationVo = new UserCreationVo("email", "password");

        User user = userVoMapper.toUser(userCreationVo);

        assertThat(user.getId()).isNotNull();
        assertThat(user.getEmail()).isEqualTo("email");
        assertThat(user.getPassword()).isEqualTo("password");
    }
}