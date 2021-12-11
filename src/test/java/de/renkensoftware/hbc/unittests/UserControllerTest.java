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

import javax.servlet.http.HttpServletRequest;

import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private final UserIncomingPort userIncomingPort = mock(UserIncomingPort.class);
    private final UserVoMapper userVoMapper = mock(UserVoMapper.class);
    private final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

    private final UserController userController =
            new UserController(userIncomingPort, userVoMapper, httpServletRequest);

    @Test
    void create() {
        UserCreationVo userCreationVo = createUserCreationVo();

        User user = createUserAtCreation();

        when(userVoMapper.toUser(userCreationVo)).thenReturn(user);

        ResponseEntity<String> response = userController.create(userCreationVo);

        verify(userIncomingPort).save(user);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void findByEmail() {
        User user = createUser();
        UserIdVo userIdVo = createUserIdVo();

        when(userIncomingPort.findByEmail(EMAIL)).thenReturn(user);
        when(userVoMapper.toIdVo(user)).thenReturn(userIdVo);

        ResponseEntity<UserIdVo> response = userController.findByEmail(EMAIL);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userIdVo);
    }

    @Test
    void addFriend() {
        UserAddFriendVo userAddFriendVo = createUserAddFriendVo();

        when(httpServletRequest.getRemoteUser()).thenReturn(USER_ID.toString());

        ResponseEntity<String> response = userController.addFriend(userAddFriendVo);

        verify(userIncomingPort).addFriend(USER_ID, FRIEND_ID);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }
}