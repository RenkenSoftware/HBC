package de.renkensoftware.hbc.domain.user.core.ports;

import de.renkensoftware.hbc.domain.user.core.model.User;

public interface UserIncomingPort {

    void save(final User user);

}
