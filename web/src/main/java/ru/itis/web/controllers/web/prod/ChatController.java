package ru.itis.web.controllers.web.prod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.itis.api.dtos.web.ChatReceivePayload;
import ru.itis.api.dtos.web.ChatSendPayload;
import ru.itis.api.dtos.web.UserDTO;
import ru.itis.api.services.UsersService;
import ru.itis.impl.utils.UserInitialsGenerator;

import java.util.Date;

@Controller
public class ChatController {

    @Autowired
    private UsersService usersService;

    @MessageMapping("/send_message")
    @SendTo("/chat/get_message")
    public ChatReceivePayload sendMessage(@Payload ChatSendPayload chatSendPayload) {

        UserDTO user = usersService.getById(Long.valueOf(chatSendPayload.getAuthorId()));
        ChatReceivePayload receivePayload = ChatReceivePayload.builder()
                .name(user.getName())
                .boardId(Long.valueOf(chatSendPayload.getBoardId()))
                .text(chatSendPayload.getText())
                .date(new Date())
                    .build();

        if (user.getAvatar() != null) {
            receivePayload.setAvatar(user.getAvatar());
        }
        else {
            String initials = UserInitialsGenerator.generate(user.getName());
            receivePayload.setInitials(initials);
        }

        return receivePayload;
    }

}
