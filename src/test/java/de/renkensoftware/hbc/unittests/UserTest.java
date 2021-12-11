package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.user.core.model.User;
import org.junit.jupiter.api.Test;

import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void UserConstructor() {
        User user = new User(EMAIL, PASSWORD);

        assertThat(user.getId()).isNotNull();
        assertThat(user.getEmail()).isEqualTo(EMAIL);
        assertThat(user.getPassword()).isEqualTo(PASSWORD);
        assertThat(user.getFriendIds()).isNotNull();
    }

    @Test
    void addFriendId() {
        User user = createUserAtCreation();

        user.addFriendId(FRIEND_ID);

        assertThat(user.getFriendIds().iterator().next()).isEqualTo(FRIEND_ID);
    }
}
