package de.renkensoftware.hbc.domain.user.core.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Getter
public class User {

    private final UUID id;

    private final String email;

    private final String password;

    private String name;

    private final Collection<UUID> friendIds = new ArrayList<>();

    public User(final String email, final String password) {
        this.email = email;
        this.password = password;
        this.id = UUID.randomUUID();
    }

    public User(final UUID id, final String email, final String password, final String name, Collection<UUID> friendIds) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name= name;
        this.friendIds.addAll(friendIds);
    }
}
