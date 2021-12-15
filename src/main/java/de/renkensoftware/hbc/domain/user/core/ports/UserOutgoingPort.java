package de.renkensoftware.hbc.domain.user.core.ports;

import de.renkensoftware.hbc.domain.user.core.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserOutgoingPort {

    void save(final User user);

    Optional<User> findByEmail(final String email);

    Optional<User> findById(final UUID id);
}
