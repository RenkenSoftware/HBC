package de.renkensoftware.hbc.domain.user.core.ports;

import de.renkensoftware.hbc.domain.user.core.model.User;

public interface UserOutgoingPort {

    void save(final User user);

}