package de.renkensoftware.hbc.domain.message.application.viewobjects;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MessagePresentationVo {

    private UUID id;

    private String senderName;

    private String content;
}
