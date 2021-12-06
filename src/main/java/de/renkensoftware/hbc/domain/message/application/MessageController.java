package de.renkensoftware.hbc.domain.message.application;

import de.renkensoftware.hbc.domain.message.application.mapper.MessageVoMapper;
import de.renkensoftware.hbc.domain.message.application.viewobjects.MessageCreationVo;
import de.renkensoftware.hbc.domain.message.core.ports.MessageIncomingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageIncomingPort messageIncomingPort;
    private final MessageVoMapper messageVoMapper;

    @PostMapping("/message")
    public ResponseEntity<String> create(@RequestBody final MessageCreationVo messageCreationVo) {
        messageIncomingPort.save(messageVoMapper.toMessage(messageCreationVo));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
