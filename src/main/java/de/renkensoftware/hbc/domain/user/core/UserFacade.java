package de.renkensoftware.hbc.domain.user.core;

import de.renkensoftware.hbc.domain.user.core.model.User;
import de.renkensoftware.hbc.domain.user.core.ports.UserIncomingPort;
import de.renkensoftware.hbc.domain.user.core.ports.UserOutgoingPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserFacade implements UserIncomingPort {

    private final UserOutgoingPort userOutgoingPort;

    @Override
    public void save(final User user) {
        userOutgoingPort.save(user);
    }
}
