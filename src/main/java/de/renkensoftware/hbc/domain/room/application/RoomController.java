package de.renkensoftware.hbc.domain.room.application;

import de.renkensoftware.hbc.domain.room.application.mapper.RoomVoMapper;
import de.renkensoftware.hbc.domain.room.application.viewobjects.RoomCreationVo;
import de.renkensoftware.hbc.domain.room.core.ports.RoomIncomingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomIncomingPort roomIncomingPort;
    private final RoomVoMapper roomVoMapper;

    @PostMapping("/room")
    public ResponseEntity<String> create(@RequestBody final RoomCreationVo roomCreationVo) {
        roomIncomingPort.save(roomVoMapper.toRoom(roomCreationVo));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
