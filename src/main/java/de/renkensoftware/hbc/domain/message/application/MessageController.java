package de.renkensoftware.hbc.domain.message.application;

import de.renkensoftware.hbc.domain.message.application.mapper.MessageVoMapper;
import de.renkensoftware.hbc.domain.message.application.viewobjects.MessageCreationVo;
import de.renkensoftware.hbc.domain.message.application.viewobjects.MessagePresentationVo;
import de.renkensoftware.hbc.domain.message.core.ports.MessageIncomingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageIncomingPort messageIncomingPort;
    private final MessageVoMapper messageVoMapper;

    private final HttpServletRequest httpServletRequest;

    @PostMapping("/message")
    public ResponseEntity<String> create(@RequestBody final MessageCreationVo messageCreationVo) {
        messageCreationVo.setSenderId(UUID.fromString(httpServletRequest.getRemoteUser()));
        messageIncomingPort.save(messageVoMapper.toMessage(messageCreationVo));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/message/room")
    public ResponseEntity<List<MessagePresentationVo>> getMessagesByRoomId(@RequestBody final UUID roomId) {
        List<MessagePresentationVo> messageList = messageIncomingPort.findAllByRoomId(roomId).stream()
                .map(messageVoMapper::toMessagePresentationVo)
                .toList();
        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }
}
