package ru.itis.web.controllers.web.prod;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itis.api.dtos.web.CommentAdditionDTO;
import ru.itis.api.dtos.web.CommentAdditionReturnDTO;
import ru.itis.api.dtos.web.CommentChangeDTO;
import ru.itis.api.services.CommentsService;

@Controller
@RequestMapping("comments")
public class CommentsController {

    private final CommentsService commentsService;

    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @PostMapping(value = "add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentAdditionReturnDTO> addComment(@RequestBody CommentAdditionDTO additionDTO) {

        CommentAdditionReturnDTO returnDTO = commentsService.saveComment(additionDTO);
        return ResponseEntity.ok(returnDTO);
    }

    @DeleteMapping("remove/{id}")
    public void removeComment(@PathVariable("id") Long id) {
        commentsService.deleteById(id);
    }

    @PatchMapping("change")
    public void changeComment(@RequestBody CommentChangeDTO changeDTO) {
        commentsService.changeComment(changeDTO);
    }

}
