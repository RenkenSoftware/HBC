package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.user.core.UserFacade;
import de.renkensoftware.hbc.domain.user.core.model.User;
import de.renkensoftware.hbc.domain.user.core.ports.UserOutgoingPort;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserFacadeTest {

    private final UserOutgoingPort userOutgoingPort = mock(UserOutgoingPort.class);

    private final UserFacade userFacade = new UserFacade(userOutgoingPort);

    @Test
    void save() {
        User user = new User("email", "password");

        userFacade.save(user);

        verify(userOutgoingPort).save(user);
    }

    @Test
    void findByEmail() {
        User user = new User(UUID.randomUUID(),
                "email",
                "password",
                "name",
                Collections.emptyList());
        String email = "email";

        when(userOutgoingPort.findByEmail(email)).thenReturn(user);

        assertThat(userFacade.findByEmail(email)).isEqualTo(user);
    }
}