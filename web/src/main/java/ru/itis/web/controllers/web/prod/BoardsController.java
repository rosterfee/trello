package ru.itis.web.controllers.web.prod;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.itis.api.dtos.web.*;
import ru.itis.api.services.BoardsService;
import ru.itis.api.services.CardsService;
import ru.itis.api.services.ColumnsService;
import ru.itis.impl.utils.UserInitialsGenerator;

import java.util.Optional;

@Controller
@RequestMapping("boards")
public class BoardsController {

    @Autowired
    private BoardsService boardsService;

    @Autowired
    private ColumnsService columnsService;

    @Autowired
    private CardsService cardsService;

    @GetMapping("{board_name}")
    public String getBoardPage(Model model,
                               @PathVariable("board_name") String boardName,
                               @AuthenticationPrincipal UserDTO user) {

        Optional<BoardDTO> optionalBoardDTO = boardsService.getByName(boardName);
        if (optionalBoardDTO.isPresent()) {
            model.addAttribute("board", optionalBoardDTO.get());
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "board not found");
        }
        model.addAttribute("user", user);
        model.addAttribute("initials", UserInitialsGenerator.generate(user.getName()));
        return "board";
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

    @ResponseBody
    @PostMapping(value = "add_column")
    public ResponseEntity<ColumnCreatingReturnDTO> addColumn(
            @RequestBody ColumnCreatingDto columnCreatingDto) {

        String name = columnCreatingDto.getName();
        Long boardId = columnCreatingDto.getBoardId();

        Long id = columnsService.addColumn(name, boardId);
        ColumnCreatingReturnDTO returnDto = new ColumnCreatingReturnDTO(id);

        return ResponseEntity.ok(returnDto);
    }

}
