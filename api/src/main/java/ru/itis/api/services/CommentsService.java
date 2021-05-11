package ru.itis.api.services;

import ru.itis.api.dtos.web.CommentAdditionDTO;

import java.util.Date;

public interface CommentsService {

    Date saveComment(CommentAdditionDTO commentAdditionDTO);

}
