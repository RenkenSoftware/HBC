package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.room.core.model.Room;
import de.renkensoftware.hbc.domain.room.infrastructure.entity.RoomEntity;
import de.renkensoftware.hbc.domain.room.infrastructure.mapper.RoomEntityMapper;
import de.renkensoftware.hbc.domain.user.infrastructure.UserJpaRepository;
import de.renkensoftware.hbc.domain.user.infrastructure.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoomEntityMapperTest {

    private final UserJpaRepository userJpaRepository = mock(UserJpaRepository.class);

    private final RoomEntityMapper roomEntityMapper = new RoomEntityMapper(userJpaRepository);


    @Test
    void toEntity() {
        UUID roomId = UUID.randomUUID();
        UserEntity userEntity = new UserEntity();
        UUID memberId = UUID.randomUUID();
        userEntity.setId(memberId);
        userEntity.setEmail("email");
        userEntity.setPassword("password");
        userEntity.setName("name");
        userEntity.setFriends(Collections.emptyList());

        Room room = new Room(roomId, List.of(memberId));

        when(userJpaRepository.getById(memberId)).thenReturn(userEntity);

        RoomEntity roomEntity = roomEntityMapper.toEntity(room);

        assertThat(roomEntity.getId()).isEqualTo(roomId);
        assertThat(roomEntity.getMembers().iterator().next()).isEqualTo(userEntity);
    }
}