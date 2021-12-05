package de.renkensoftware.hbc.domain.user.application;

import de.renkensoftware.hbc.domain.user.application.mapper.UserVoMapper;
import de.renkensoftware.hbc.domain.user.application.viewobjects.UserCreationVo;
import de.renkensoftware.hbc.domain.user.core.ports.UserIncomingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserIncomingPort userIncomingPort;
    private final UserVoMapper userVoMapper;

    @PostMapping("/user")
    public ResponseEntity<String> create(UserCreationVo userCreationVo) {
        userIncomingPort.save(userVoMapper.toUser(userCreationVo));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
