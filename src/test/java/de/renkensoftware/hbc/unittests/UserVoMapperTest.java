package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.user.application.mapper.UserVoMapper;
import de.renkensoftware.hbc.domain.user.application.viewobjects.UserCreationVo;
import de.renkensoftware.hbc.domain.user.application.viewobjects.UserIdVo;
import de.renkensoftware.hbc.domain.user.core.model.User;
import org.junit.jupiter.api.Test;

import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

class UserVoMapperTest {

    private final UserVoMapper userVoMapper = new UserVoMapper();

    @Test
    void toUser() {
        UserCreationVo userCreationVo = createUserCreationVo();

        User user = userVoMapper.toUser(userCreationVo);

        assertThat(user.getId()).isNotNull();
        assertThat(user.getEmail()).isEqualTo(EMAIL);
        assertThat(user.getPassword()).isEqualTo(PASSWORD);
    }

    @Test
    void toIdVo() {
        User user = createUser();

        UserIdVo userIdVo = userVoMapper.toIdVo(user);

        assertThat(userIdVo.getId()).isEqualTo(user.getId());
    }
}