package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.user.core.model.User;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void UserConstructor1() {
        User user = new User("email", "password");

        assertThat(user.getId()).isNotNull();
        assertThat(user.getEmail()).isEqualTo("email");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getFriendIds()).isNotNull();
    }

    @Test
    void UserConstructor2() {
        UUID id = UUID.randomUUID();
        Collection<UUID> friendIds = List.of(UUID.randomUUID());
        User user = new User(id, "email", "password", "name", friendIds);

        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getEmail()).isEqualTo("email");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getName()).isEqualTo("name");
        assertThat(user.getFriendIds()).hasSize(1);
    }

    @Test
    void addFriendId() {
        User user = new User(UUID.randomUUID(), "email", "password", "name", Collections.emptyList());
        UUID friendId = UUID.randomUUID();

        user.addFriendId(friendId);

        assertThat(user.getFriendIds().iterator().next()).isEqualTo(friendId);
    }
}
