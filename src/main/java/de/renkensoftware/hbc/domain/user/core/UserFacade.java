package de.renkensoftware.hbc.domain.user.core;

import de.renkensoftware.hbc.domain.user.core.model.User;
import de.renkensoftware.hbc.domain.user.core.ports.UserIncomingPort;
import de.renkensoftware.hbc.domain.user.core.ports.UserOutgoingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserFacade implements UserIncomingPort {

    private final UserOutgoingPort userOutgoingPort;

    @Override
    public void save(final User user) {
        userOutgoingPort.save(user);
    }

    @Override
    public User findByEmail(final String email) {
        return userOutgoingPort.findByEmail(email);
    }

    @Override
    public void addFriend(final UUID id, final UUID friendId) {
        User user = userOutgoingPort.findById(id);
        user.addFriendId(friendId);
        userOutgoingPort.save(user);

        User friend = userOutgoingPort.findById(friendId);
        friend.addFriendId(id);
        userOutgoingPort.save(friend);
    }
}
