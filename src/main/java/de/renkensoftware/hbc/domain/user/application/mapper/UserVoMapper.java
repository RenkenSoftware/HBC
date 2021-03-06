package de.renkensoftware.hbc.domain.user.application.mapper;

import de.renkensoftware.hbc.domain.user.application.viewobjects.UserCreationVo;
import de.renkensoftware.hbc.domain.user.application.viewobjects.UserIdVo;
import de.renkensoftware.hbc.domain.user.core.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserVoMapper {

    public User toUser(final UserCreationVo userCreationVo) {
        return new User(userCreationVo.getEmail(), userCreationVo.getPassword());
    }

    public UserIdVo toIdVo(final User user) {
        UserIdVo userIdVo = new UserIdVo();
        userIdVo.setId(user.getId());
        return userIdVo;
    }
}
