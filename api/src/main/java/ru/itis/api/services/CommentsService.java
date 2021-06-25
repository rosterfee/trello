package ru.itis.api.services;

import ru.itis.api.dtos.web.CommentAdditionDTO;
import ru.itis.api.dtos.web.CommentAdditionReturnDTO;
import ru.itis.api.dtos.web.CommentChangeDTO;

import java.util.Date;

public interface CommentsService {

    CommentAdditionReturnDTO saveComment(CommentAdditionDTO commentAdditionDTO);

    void deleteById(Long id);

    void changeComment(CommentChangeDTO commentChangeDTO);
}
