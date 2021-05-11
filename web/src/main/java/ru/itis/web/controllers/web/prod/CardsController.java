package ru.itis.web.controllers.web.prod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.itis.api.dtos.web.*;
import ru.itis.api.services.CardsService;
import ru.itis.api.services.CheckListsService;
import ru.itis.api.services.CommentsService;
import ru.itis.impl.utils.UserInitialsGenerator;

import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("cards")
public class CardsController {

    @Autowired
    private CardsService cardsService;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private CheckListsService checkListsService;

    @GetMapping("{card_id}")
    public String showCardContent(@PathVariable("card_id") Long cardId,
                                  @AuthenticationPrincipal UserDTO user,
                                  Model model) {

        CardDTO cardDTO;

        Optional<CardDTO> optionalCardDTO = cardsService.getById(cardId);;
        if (optionalCardDTO.isPresent()) {
            cardDTO = optionalCardDTO.get();
            System.out.println("checklists: " + cardDTO.getCheckLists());
        }
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "card not found");

        model.addAttribute("card", cardDTO);
        model.addAttribute("user", user);

        if (user.getAvatar() == null) {
            model.addAttribute("initials", UserInitialsGenerator.generate(user.getName()));
        }

        return "card_content";
    }

    @PostMapping(value = "save_description/{card_id}")
    public ResponseEntity<?> saveDescription(@RequestBody DescriptionSavingDTO savingDTO,
                                             @PathVariable("card_id") Long cardId) {

        System.out.println(savingDTO.getText());
        cardsService.saveDescription(savingDTO.getText(), cardId);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "add_comment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentAdditionReturnDTO> addComment(@RequestBody CommentAdditionDTO additionDTO) {

        System.out.println(additionDTO);

        Date createdAt = commentsService.saveComment(additionDTO);
        CommentAdditionReturnDTO commentAdditionReturnDTO =
                new CommentAdditionReturnDTO(createdAt);

        return ResponseEntity.ok(commentAdditionReturnDTO);

    }

    @PostMapping(value = "add_checkList",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CheckListAddReturnDTO> addCheckList(
            @RequestBody CheckListAdditionDTO additionDTO) {

        Long id = checkListsService.saveCheckList(additionDTO);
        CheckListAddReturnDTO returnDTO = new CheckListAddReturnDTO(id);
        return ResponseEntity.ok(returnDTO);

    }

    @PostMapping("add_checkList_task")
    public ResponseEntity<CheckListTaskAddReturnDTO> addCheckListTask(
            @RequestBody CheckListTaskAdditionDTO additionDTO) {

        Long id = checkListsService.saveTask(additionDTO);
        CheckListTaskAddReturnDTO returnDTO = new CheckListTaskAddReturnDTO(id);

        return ResponseEntity.ok(returnDTO);

    }

    @PatchMapping("change_task_status")
    public ResponseEntity<?>addCheckListTask(@RequestBody ChangeTaskStatusDTO changeTaskStatusDTO) {

        checkListsService.changeTaskStatus(changeTaskStatusDTO);
        return ResponseEntity.ok().build();

    }

}
