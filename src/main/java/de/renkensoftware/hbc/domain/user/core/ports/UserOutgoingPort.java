package de.renkensoftware.hbc.domain.user.core.ports;

import de.renkensoftware.hbc.domain.user.core.model.User;

import java.util.UUID;

public interface UserOutgoingPort {

    void save(final User user);

    User findByEmail(final String email);

    User findById(final UUID id);
}
