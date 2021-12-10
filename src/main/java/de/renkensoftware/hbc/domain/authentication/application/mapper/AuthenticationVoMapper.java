package de.renkensoftware.hbc.domain.authentication.application.mapper;

import de.renkensoftware.hbc.domain.authentication.application.viewobjects.AuthenticationVo;
import de.renkensoftware.hbc.domain.authentication.core.model.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationVoMapper {

    public Authentication toAuthentication(final AuthenticationVo authenticationVo) {
        return new Authentication(authenticationVo.getEmail(), authenticationVo.getPassword());
    }
}
