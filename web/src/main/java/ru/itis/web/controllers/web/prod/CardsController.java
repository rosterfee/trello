package ru.itis.web.controllers.web.prod;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.itis.api.dtos.web.*;
import ru.itis.api.services.CardsService;

import java.util.Optional;

@Controller
@RequestMapping("cards")
public class CardsController {

    private final CardsService cardsService;

    public CardsController(CardsService cardsService) {
        this.cardsService = cardsService;
    }

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

    @ResponseBody
    @PostMapping(value = "add_card")
    public ResponseEntity<CardCreatingReturnDTO> addCard(@RequestBody CardCreatingDto cardCreatingDto) throws JsonProcessingException {

        Optional<Long> optionalId = cardsService.save(cardCreatingDto.getTitle(),
                cardCreatingDto.getColumnId());

        if (optionalId.isPresent()) {
            CardCreatingReturnDTO returnDTO = new CardCreatingReturnDTO(optionalId.get());
            return ResponseEntity.ok(returnDTO);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "column with " +
                    "such id not found");
        }

    }

    @PostMapping(value = "save_description/{card_id}")
    public ResponseEntity<?> saveDescription(@RequestBody DescriptionSavingDTO savingDTO,
                                             @PathVariable("card_id") Long cardId) {

        System.out.println(savingDTO.getText());
        cardsService.saveDescription(savingDTO.getText(), cardId);

        return ResponseEntity.ok().build();
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
