package ru.itis.web.controllers.web.prod;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itis.api.dtos.web.*;
import ru.itis.api.exceptions.ResourceNotFoundException;
import ru.itis.api.services.BoardsService;
import ru.itis.api.services.UsersService;
import ru.itis.impl.utils.UserInitialsGenerator;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("boards")
public class BoardsController {

    private final BoardsService boardsService;
    private final UsersService usersService;

    private final List<String> options = Arrays.asList("private", "public");


    public BoardsController(BoardsService boardsService, UsersService usersService) {
        this.boardsService = boardsService;
        this.usersService = usersService;
    }

    @PostMapping("create")
    public String createBoard(@Valid @ModelAttribute BoardCreatingForm form,
                              BindingResult bindingResult,
                              Model model,
                              @AuthenticationPrincipal UserDTO user) throws IOException {

        System.out.println(form.getType());

        if (bindingResult.hasErrors()) {
            model.addAttribute("options", options);
            model.addAttribute(user);
            model.addAttribute("boardCreatingForm", form);
            model.addAttribute("initials", UserInitialsGenerator.generate(user.getName()));
            return "home";
        }
        else {
            boardsService.save(form, user);
            return "redirect:/boards/" + form.getName();
        }

    }

    @GetMapping("{board_name}")
    public String getBoardPage(Model model,
                               @PathVariable("board_name") String boardName,
                               @AuthenticationPrincipal UserDTO user) {

        Optional<BoardDTO> optionalBoardDTO = boardsService.getByName(boardName);
        if (optionalBoardDTO.isPresent()) {

            BoardDTO board = optionalBoardDTO.get();

            board.getColumns().forEach(column -> Collections.sort(column.getCards()));

            boardsService.addBoardParticipant(board, user);

            model.addAttribute("board", board);
            model.addAttribute("user", user);

            return "board";
        }
        else {
            throw new ResourceNotFoundException("Board not found");
        }
    }

    @ResponseBody
    @GetMapping("get_users_to_inv/{email}")
    public ResponseEntity<List<UserDTO>> getUsersToInvite(@PathVariable("email") String email) {

        List<UserDTO> users = usersService.getAllByEmailContaining(email);
        return ResponseEntity.ok(users);

    }

}
