package de.renkensoftware.hbc.domain.message.application.viewobjects;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MessageCreationVo {

    private UUID senderId;

    private UUID roomId;

    private String content;
}
