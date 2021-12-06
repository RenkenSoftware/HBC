package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.user.application.UserController;
import de.renkensoftware.hbc.domain.user.application.mapper.UserVoMapper;
import de.renkensoftware.hbc.domain.user.application.viewobjects.UserCreationVo;
import de.renkensoftware.hbc.domain.user.core.model.User;
import de.renkensoftware.hbc.domain.user.core.ports.UserIncomingPort;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private final UserIncomingPort userIncomingPort = mock(UserIncomingPort.class);
    private final UserVoMapper userVoMapper = mock(UserVoMapper.class);

    private final UserController userController = new UserController(userIncomingPort, userVoMapper);

    @Test
    void create() {
        UserCreationVo userCreationVo = new UserCreationVo();
        userCreationVo.setEmail("email");
        userCreationVo.setPassword("password");

        User user = new User("email", "password");

        when(userVoMapper.toUser(userCreationVo)).thenReturn(user);

        ResponseEntity<String> response = userController.create(userCreationVo);

        verify(userIncomingPort).save(user);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}