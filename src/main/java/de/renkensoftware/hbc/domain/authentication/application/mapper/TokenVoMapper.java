package de.renkensoftware.hbc.domain.authentication.application.mapper;

import de.renkensoftware.hbc.domain.authentication.application.viewobjects.TokenVo;
import de.renkensoftware.hbc.domain.authentication.core.model.Token;
import org.springframework.stereotype.Service;

@Service
public class TokenVoMapper {

    public TokenVo toVo(final Token token) {
        TokenVo tokenVo = new TokenVo();
        tokenVo.setTokenString(token.getTokenString());
        return tokenVo;
    }
}
