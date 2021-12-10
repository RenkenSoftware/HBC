package de.renkensoftware.hbc.domain.authentication.core.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Authentication {

    private final String email;

    private final String password;
}
