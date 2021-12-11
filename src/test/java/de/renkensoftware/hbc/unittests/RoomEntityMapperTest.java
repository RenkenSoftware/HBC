package de.renkensoftware.hbc.unittests;

import de.renkensoftware.hbc.domain.room.core.model.Room;
import de.renkensoftware.hbc.domain.room.infrastructure.entity.RoomEntity;
import de.renkensoftware.hbc.domain.room.infrastructure.mapper.RoomEntityMapper;
import de.renkensoftware.hbc.domain.user.infrastructure.UserJpaRepository;
import de.renkensoftware.hbc.domain.user.infrastructure.entity.UserEntity;
import org.junit.jupiter.api.Test;

import static de.renkensoftware.hbc.testdatafactories.RoomTestDataFactory.ROOM_ID;
import static de.renkensoftware.hbc.testdatafactories.RoomTestDataFactory.createRoom;
import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.USER_ID;
import static de.renkensoftware.hbc.testdatafactories.UserTestDataFactory.createUserEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoomEntityMapperTest {

    private final UserJpaRepository userJpaRepository = mock(UserJpaRepository.class);

    private final RoomEntityMapper roomEntityMapper = new RoomEntityMapper(userJpaRepository);


    @Test
    void toEntity() {
        UserEntity userEntity = createUserEntity();

        Room room = createRoom();

        when(userJpaRepository.getById(USER_ID)).thenReturn(userEntity);

        RoomEntity roomEntity = roomEntityMapper.toEntity(room);

        assertThat(roomEntity.getId()).isEqualTo(ROOM_ID);
        assertThat(roomEntity.getMembers().iterator().next()).isEqualTo(userEntity);
    }
}