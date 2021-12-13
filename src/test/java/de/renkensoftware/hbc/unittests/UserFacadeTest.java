package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.user.core.UserFacade;
import de.renkensoftware.hbc.domain.user.core.model.User;
import de.renkensoftware.hbc.domain.user.core.ports.UserOutgoingPort;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserFacadeTest {

    private final UserOutgoingPort userOutgoingPort = mock(UserOutgoingPort.class);

    private final UserFacade userFacade = new UserFacade(userOutgoingPort);

    @Test
    void save() {
        User user = createUser();

        userFacade.save(user);

        verify(userOutgoingPort).save(user);
    }

    @Test
    void findByEmail() {
        User user = createUser();

        when(userOutgoingPort.findByEmail(EMAIL)).thenReturn(user);

        assertThat(userFacade.findByEmail(EMAIL)).isEqualTo(user);
    }

    @Test
    void addFriend() {
        User user = createUserAtCreation();
        User friend = new User(FRIEND_ID, FRIEND_EMAIL, FRIEND_PASSWORD, FRIEND_NAME, new ArrayList<>());
        when(userOutgoingPort.findById(USER_ID)).thenReturn(user);
        when(userOutgoingPort.findById(FRIEND_ID)).thenReturn(friend);

        userFacade.addFriend(USER_ID, FRIEND_ID);

        assertThat(user.getFriendIds().iterator().next()).isEqualTo(FRIEND_ID);
        assertThat(friend.getFriendIds().iterator().next()).isEqualTo(USER_ID);

        verify(userOutgoingPort).save(user);
        verify(userOutgoingPort).save(friend);
    }

    @Test
    void findById() {
        User user = createUser();

        when(userOutgoingPort.findById(USER_ID)).thenReturn(user);

        assertThat(userFacade.findById(USER_ID)).isEqualTo(user);
    }
}