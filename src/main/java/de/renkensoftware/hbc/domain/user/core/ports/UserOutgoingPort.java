package de.renkensoftware.hbc.domain.user.core.ports;

import de.renkensoftware.hbc.domain.user.core.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserOutgoingPort {

    void save(final User user);

}
