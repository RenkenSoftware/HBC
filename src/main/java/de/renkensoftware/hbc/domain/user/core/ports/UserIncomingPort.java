package de.renkensoftware.hbc.domain.user.core.ports;

import de.renkensoftware.hbc.domain.user.core.model.User;

import java.util.UUID;

public interface UserIncomingPort {

    void save(final User user);

    User findById(final UUID id);

    User findByEmail(final String email);

    void addFriend(final UUID id, final UUID friendId);
}
