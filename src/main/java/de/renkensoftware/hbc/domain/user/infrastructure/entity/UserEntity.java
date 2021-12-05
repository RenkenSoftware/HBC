package de.renkensoftware.hbc.domain.user.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;
import java.util.UUID;

@Entity
@Getter
@Setter
public class UserEntity {

    @Id
    private UUID id;

    private String email;

    private String password;

    private String name;

    @ManyToMany
    private Collection<UserEntity> friends;
}
