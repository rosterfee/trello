package ru.itis.api.dtos.web;

import lombok.Data;

@Data
public class ChatSendPayload {

    private String authorId;
    private String boardId;
    private String text;

}
