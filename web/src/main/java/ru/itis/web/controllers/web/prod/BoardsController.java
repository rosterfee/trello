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
import ru.itis.api.exceptions.ResourceNotFoundException;
import ru.itis.api.services.BoardsService;
import ru.itis.api.services.CardsService;
import ru.itis.api.services.ColumnsService;
import ru.itis.api.services.UsersService;
import ru.itis.impl.utils.UserInitialsGenerator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("boards")
public class BoardsController {

    @Autowired
    private BoardsService boardsService;

    @Autowired
    private ColumnsService columnsService;

    @Autowired
    private CardsService cardsService;

    @Autowired
    private UsersService usersService;

    @GetMapping("{board_name}")
    public String getBoardPage(Model model,
                               @PathVariable("board_name") String boardName,
                               @AuthenticationPrincipal UserDTO user) {

        Optional<BoardDTO> optionalBoardDTO = boardsService.getByName(boardName);
        if (optionalBoardDTO.isPresent()) {

            BoardDTO board = optionalBoardDTO.get();
            boardsService.addBoardParticipant(board, user);

//            Collections.sort(board.getColumns());
            model.addAttribute("board", board);
            model.addAttribute("user", user);

            return "board";
        }
        else {
            throw new ResourceNotFoundException("Board not found");
        }
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

    @ResponseBody
    @GetMapping("get_users_to_inv/{email}")
    public ResponseEntity<List<UserDTO>> getUsersToInvite(@PathVariable("email") String email) {

        List<UserDTO> users = usersService.getAllByEmailContaining(email);
        return ResponseEntity.ok(users);

    }

}
