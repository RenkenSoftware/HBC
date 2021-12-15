package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.user.core.UserFacade;
import de.renkensoftware.hbc.domain.user.core.model.User;
import de.renkensoftware.hbc.domain.user.core.ports.UserOutgoingPort;
import de.renkensoftware.hbc.exception.EmailAlreadyRegisteredException;
import de.renkensoftware.hbc.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserFacadeTest {

    private final UserOutgoingPort userOutgoingPort = mock(UserOutgoingPort.class);

    private final UserFacade userFacade = new UserFacade(userOutgoingPort);

    @Test
    void create() {
        User user = createUserWithoutFriend();

        userFacade.create(user);

        verify(userOutgoingPort).save(user);
    }

    @Test
    void createAlreadyExistingUser() {
        User user = createUserWithoutFriend();

        when(userOutgoingPort.findByEmail(EMAIL)).thenReturn(Optional.of(user));

        assertThrows(EmailAlreadyRegisteredException.class, () -> userFacade.create(user));
    }

    @Test
    void findByEmail() {
        User user = createUser();

        when(userOutgoingPort.findByEmail(EMAIL)).thenReturn(Optional.of(user));

        assertThat(userFacade.findByEmail(EMAIL)).isEqualTo(user);
    }

    @Test
    void findByEmailWithoutResult() {
        when(userOutgoingPort.findByEmail(EMAIL)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userFacade.findByEmail(EMAIL));
    }

    @Test
    void addFriend() {
        User user = createUserWithoutFriend();
        User friend = new User(FRIEND_ID, FRIEND_EMAIL, FRIEND_PASSWORD, FRIEND_NAME, new ArrayList<>());

        when(userOutgoingPort.findById(USER_ID)).thenReturn(Optional.of(user));
        when(userOutgoingPort.findById(FRIEND_ID)).thenReturn(Optional.of(friend));

        userFacade.addFriend(USER_ID, FRIEND_ID);

        assertThat(user.getFriendIds().iterator().next()).isEqualTo(FRIEND_ID);
        assertThat(friend.getFriendIds().iterator().next()).isEqualTo(USER_ID);

        verify(userOutgoingPort).save(user);
        verify(userOutgoingPort).save(friend);
    }

    @Test
    void addFriendToNonExistingUser() {
        when(userOutgoingPort.findById(USER_ID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userFacade.addFriend(USER_ID, FRIEND_ID));
    }

    @Test
    void addNonExistingFriend() {
        User user = createUserWithoutFriend();

        when(userOutgoingPort.findById(USER_ID)).thenReturn(Optional.of(user));
        when(userOutgoingPort.findById(FRIEND_ID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userFacade.addFriend(USER_ID, FRIEND_ID));
    }

    @Test
    void findById() {
        User user = createUser();

        when(userOutgoingPort.findById(USER_ID)).thenReturn(Optional.of(user));

        assertThat(userFacade.findById(USER_ID)).isEqualTo(user);
    }

    @Test
    void findByIdWithoutResult() {
        when(userOutgoingPort.findById(USER_ID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userFacade.findById(USER_ID));
    }
}
