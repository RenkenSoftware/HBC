package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.user.application.UserController;
import de.renkensoftware.hbc.domain.user.application.mapper.UserVoMapper;
import de.renkensoftware.hbc.domain.user.application.viewobjects.UserAddFriendVo;
import de.renkensoftware.hbc.domain.user.application.viewobjects.UserCreationVo;
import de.renkensoftware.hbc.domain.user.application.viewobjects.UserIdVo;
import de.renkensoftware.hbc.domain.user.core.model.User;
import de.renkensoftware.hbc.domain.user.core.ports.UserIncomingPort;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.UUID;

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

    @Test
    void findByEmail() {
        UUID id = UUID.randomUUID();
        String email = "email";
        User user = new User(id, email, "password", "name", Collections.emptyList());
        UserIdVo userIdVo = new UserIdVo();
        userIdVo.setId(id);

        when(userIncomingPort.findByEmail(email)).thenReturn(user);
        when(userVoMapper.toIdVo(user)).thenReturn(userIdVo);

        ResponseEntity<UserIdVo> response = userController.findByEmail(email);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userIdVo);
    }

    @Test
    void addFriend() {
        UUID id = UUID.randomUUID();
        UUID friendId = UUID.randomUUID();

        UserAddFriendVo userAddFriendVo = new UserAddFriendVo();
        userAddFriendVo.setId(id);
        userAddFriendVo.setFriendId(friendId);

        ResponseEntity<String> response = userController.addFriend(userAddFriendVo);

        verify(userIncomingPort).addFriend(id, friendId);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }
}