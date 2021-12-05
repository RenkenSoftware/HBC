package de.renkensoftware.hbc.domain.user.application.viewobjects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCreationVo {

    private final String email;

    private final String password;
}
