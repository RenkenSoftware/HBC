package de.renkensoftware.hbc.domain.user.application.viewobjects;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserAddFriendVo {

    private UUID id;

    private UUID friendId;
}
