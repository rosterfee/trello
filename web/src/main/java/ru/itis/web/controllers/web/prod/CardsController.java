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

        CommentAdditionReturnDTO returnDTO = commentsService.saveComment(additionDTO);
        return ResponseEntity.ok(returnDTO);

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
    public ResponseEntity<?> addCheckListTask(@RequestBody ChangeTaskStatusDTO changeTaskStatusDTO) {

        checkListsService.changeTaskStatus(changeTaskStatusDTO);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("remove_checklist/{id}")
    public void removeChecklist(@PathVariable("id") Long id) {
        System.out.println("hello");
        checkListsService.deleteById(id);
    }

    @DeleteMapping("remove_comment/{id}")
    public void removeComment(@PathVariable("id") Long id) {
        commentsService.deleteById(id);
    }

    @PatchMapping("change_comment")
    public void changeComment(@RequestBody CommentChangeDTO changeDTO) {
        commentsService.changeComment(changeDTO);
    }

    @DeleteMapping("remove_task/{id}")
    public void removeTask(@PathVariable("id") Long id) {
        checkListsService.deleteTaskById(id);
    }

    @DeleteMapping("delete/{id}")
    public void deleteCard(@PathVariable("id") Long id) {
        cardsService.delete(id);
    }

    @PatchMapping("move/{cardId}/{columnId}")
    public void moveCard(@PathVariable("cardId") Long cardId,
                         @PathVariable("columnId") Long columnId) {
        cardsService.moveCard(columnId, cardId);
    }

}
