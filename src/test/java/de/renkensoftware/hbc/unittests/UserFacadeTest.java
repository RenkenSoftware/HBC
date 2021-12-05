package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.user.core.UserFacade;
import de.renkensoftware.hbc.domain.user.core.model.User;
import de.renkensoftware.hbc.domain.user.core.ports.UserOutgoingPort;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserFacadeTest {

    private final UserOutgoingPort userOutgoingPort = mock(UserOutgoingPort.class);

    private final UserFacade userFacade = new UserFacade(userOutgoingPort);

    @Test
    void save() {
        User user = new User("email", "password");

        userFacade.save(user);

        verify(userOutgoingPort).save(user);
    }
}