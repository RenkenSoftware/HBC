package de.renkensoftware.hbc.domain.message.application.viewobjects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class MessageCreationVo {

    private final UUID senderId;

    private final UUID roomId;

    private final String content;
}
