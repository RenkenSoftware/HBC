package de.renkensoftware.hbc.testdatafactories;

import de.renkensoftware.hbc.domain.user.application.viewobjects.UserAddFriendVo;
import de.renkensoftware.hbc.domain.user.application.viewobjects.UserCreationVo;
import de.renkensoftware.hbc.domain.user.application.viewobjects.UserIdVo;
import de.renkensoftware.hbc.domain.user.core.model.User;
import de.renkensoftware.hbc.domain.user.infrastructure.entity.UserEntity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class UserTestDataFactory {

    public static UUID USER_ID = UUID.randomUUID();
    public static String EMAIL = "testemail";
    public static String PASSWORD = "testpassword";
    public static String NAME = "testname";

    public static UUID FRIEND_ID = UUID.randomUUID();
    public static String FRIEND_EMAIL = "friendemail";
    public static String FRIEND_PASSWORD = "friendpassword";
    public static String FRIEND_NAME = "friendname";

    public static UserIdVo createUserIdVo() {
        UserIdVo userIdVo = new UserIdVo();
        userIdVo.setId(USER_ID);
        return userIdVo;
    }

    public static UserCreationVo createUserCreationVo() {
        UserCreationVo userCreationVo = new UserCreationVo();
        userCreationVo.setEmail(EMAIL);
        userCreationVo.setPassword(PASSWORD);
        return userCreationVo;
    }

    public static UserAddFriendVo createUserAddFriendVo() {
        UserAddFriendVo userAddFriendVo = new UserAddFriendVo();
        userAddFriendVo.setFriendId(FRIEND_ID);
        return userAddFriendVo;
    }

    public static User createUserAtCreation() {
        return new User(EMAIL, PASSWORD);
    }

    public static User createUser() {
        return new User(USER_ID, EMAIL, PASSWORD, NAME, List.of(FRIEND_ID));
    }

    public static User createUserWithoutFriend() {
        return new User(USER_ID, EMAIL, PASSWORD, NAME, Collections.emptyList());
    }

    public static UserEntity createUserEntity() {
        UserEntity friendEntity = new UserEntity();
        friendEntity.setId(FRIEND_ID);
        friendEntity.setEmail(FRIEND_EMAIL);
        friendEntity.setPassword(FRIEND_PASSWORD);
        friendEntity.setName(FRIEND_NAME);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);
        userEntity.setEmail(EMAIL);
        userEntity.setPassword(PASSWORD);
        userEntity.setName(NAME);
        userEntity.setFriends(List.of(friendEntity));
        friendEntity.setFriends(List.of(userEntity));
        return userEntity;
    }

    public static UserEntity createUserEntityWithoutFriend() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);
        userEntity.setEmail(EMAIL);
        userEntity.setPassword(PASSWORD);
        userEntity.setName(NAME);
        return userEntity;
    }

    public static UserEntity createFriendEntity() {
        UserEntity friendEntity = new UserEntity();
        friendEntity.setId(FRIEND_ID);
        friendEntity.setEmail(FRIEND_EMAIL);
        friendEntity.setPassword(FRIEND_PASSWORD);
        friendEntity.setName(FRIEND_NAME);
        return friendEntity;
    }
}
