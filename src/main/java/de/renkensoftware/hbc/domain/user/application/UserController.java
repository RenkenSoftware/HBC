package de.renkensoftware.hbc.domain.user.application;

import de.renkensoftware.hbc.domain.user.application.mapper.UserVoMapper;
import de.renkensoftware.hbc.domain.user.application.viewobjects.UserAddFriendVo;
import de.renkensoftware.hbc.domain.user.application.viewobjects.UserCreationVo;
import de.renkensoftware.hbc.domain.user.application.viewobjects.UserIdVo;
import de.renkensoftware.hbc.domain.user.core.ports.UserIncomingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserIncomingPort userIncomingPort;
    private final UserVoMapper userVoMapper;

    @PostMapping("/user/create")
    public ResponseEntity<String> create(@RequestBody final UserCreationVo userCreationVo) {
        userIncomingPort.save(userVoMapper.toUser(userCreationVo));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/user/email")
    public ResponseEntity<UserIdVo> findByEmail(@RequestBody final String email) {
        UserIdVo userIdVo = userVoMapper.toIdVo(userIncomingPort.findByEmail(email));
        return new ResponseEntity<>(userIdVo, HttpStatus.OK);
    }

    @PutMapping("/user/addfriend")
    public ResponseEntity<String> addFriend(@RequestBody final UserAddFriendVo userAddFriendVo) {
        userIncomingPort.addFriend(userAddFriendVo.getId(), userAddFriendVo.getFriendId());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
