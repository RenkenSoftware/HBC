package de.renkensoftware.hbc.domain.authentication.core.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Token {

    private final String tokenString;
}
