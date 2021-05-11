package ru.itis.api.dtos.web;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ChatReceivePayload {

    private String avatar;
    private String initials;
    private String name;
    private Long boardId;
    private String text;
    private Date date;

}
