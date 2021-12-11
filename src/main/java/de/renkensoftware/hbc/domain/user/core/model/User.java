package de.renkensoftware.hbc.domain.user.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class User {

    private final UUID id;

    private final String email;

    private final String password;

    private String name;

    private final Collection<UUID> friendIds;

    public User(final String email, final String password) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.password = password;
        this.friendIds = new ArrayList<>();
    }

    public void addFriendId(final UUID friendId) {
        this.friendIds.add(friendId);
    }
}
