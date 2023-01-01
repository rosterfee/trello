package ru.itis.web.controllers.web.prod;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.api.dtos.web.BoardCreatingForm;
import ru.itis.api.dtos.web.BoardDTO;
import ru.itis.api.dtos.web.UserDTO;
import ru.itis.api.services.BoardsService;
import ru.itis.api.services.UsersService;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("home")
public class HomeController {

    private final BoardsService boardsService;
    private final UsersService usersService;

    private final List<String> options = Arrays.asList("private", "public");

    public HomeController(BoardsService boardsService, UsersService usersService) {
        this.boardsService = boardsService;
        this.usersService = usersService;
    }

    @GetMapping
    public String getHomePage(@AuthenticationPrincipal UserDTO user, Model model) {

        user = usersService.getById(user.getId());
        model.addAttribute("user", user);

        List<BoardDTO> userBoards = boardsService.getUserBoards(user);
        model.addAttribute("boards", userBoards);

        model.addAttribute("boardCreatingForm", new BoardCreatingForm());
        model.addAttribute("options", options);

        return "home";
    }

}
