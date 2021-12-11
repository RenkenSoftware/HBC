package de.renkensoftware.hbc.testdatafactories;

import de.renkensoftware.hbc.domain.authentication.application.viewobjects.AuthenticationVo;
import de.renkensoftware.hbc.domain.authentication.core.model.Authentication;

import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.EMAIL;
import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.PASSWORD;

public abstract class AuthenticationTestDataFactory {

    public static Authentication createAuthentication() {
        return new Authentication(EMAIL, PASSWORD);
    }

    public static AuthenticationVo createAuthenticationVo() {
        AuthenticationVo authenticationVo = new AuthenticationVo();
        authenticationVo.setEmail(EMAIL);
        authenticationVo.setPassword(PASSWORD);
        return authenticationVo;
    }
}
