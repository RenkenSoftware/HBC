package de.renkensoftware.hbc.domain.user.core;

import de.renkensoftware.hbc.domain.user.core.model.User;
import de.renkensoftware.hbc.domain.user.core.ports.UserIncomingPort;
import de.renkensoftware.hbc.domain.user.core.ports.UserOutgoingPort;
import de.renkensoftware.hbc.exception.EmailAlreadyRegisteredException;
import de.renkensoftware.hbc.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserFacade implements UserIncomingPort {

    private final UserOutgoingPort userOutgoingPort;

    @Override
    public void create(final User user) {
        checkIfEmailIsAlreadyRegistered(user.getEmail());
        userOutgoingPort.save(user);
    }

    @Override
    public User findById(final UUID id) {
        return userOutgoingPort.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User findByEmail(final String email) {
        return userOutgoingPort.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void addFriend(final UUID id, final UUID friendId) {
        User user = userOutgoingPort.findById(id).orElseThrow(UserNotFoundException::new);
        User friend = userOutgoingPort.findById(friendId).orElseThrow(UserNotFoundException::new);

        user.addFriendId(friendId);
        userOutgoingPort.save(user);

        friend.addFriendId(id);
        userOutgoingPort.save(friend);
    }

    private void checkIfEmailIsAlreadyRegistered(final String email) {
        if (userOutgoingPort.findByEmail(email).isPresent()) {
            throw new EmailAlreadyRegisteredException();
        }
    }
}
