package de.renkensoftware.hbc.domain.authentication.application;

import de.renkensoftware.hbc.domain.authentication.application.mapper.AuthenticationVoMapper;
import de.renkensoftware.hbc.domain.authentication.application.mapper.TokenVoMapper;
import de.renkensoftware.hbc.domain.authentication.application.viewobjects.AuthenticationVo;
import de.renkensoftware.hbc.domain.authentication.application.viewobjects.TokenVo;
import de.renkensoftware.hbc.domain.authentication.core.model.Token;
import de.renkensoftware.hbc.domain.authentication.core.ports.AuthenticationIncomingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationIncomingPort authenticationIncomingPort;
    private final AuthenticationVoMapper authenticationVoMapper;
    private final TokenVoMapper tokenVoMapper;

    @PostMapping("/authenticate")
    public ResponseEntity<TokenVo> createToken(@RequestBody final AuthenticationVo authenticationVo) {
        Token token = authenticationIncomingPort.createToken(authenticationVoMapper.toAuthentication(authenticationVo));
        return new ResponseEntity<>(tokenVoMapper.toVo(token), HttpStatus.CREATED);
    }
}
