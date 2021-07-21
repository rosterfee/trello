package ru.itis.web.controllers.web.prod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itis.api.dtos.web.BoardCreatingForm;
import ru.itis.api.dtos.web.UserDTO;
import ru.itis.api.services.BoardsService;
import ru.itis.api.services.UsersService;
import ru.itis.impl.utils.UserInitialsGenerator;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("home")
public class HomeController {

    @Autowired
    private BoardsService boardsService;

    @Autowired
    private UsersService usersService;

    private final List<String> options = Arrays.asList("private", "public");

    @GetMapping
    public String boardsPage(@AuthenticationPrincipal UserDTO user, Model model) {

        user = usersService.getById(user.getId());
        System.out.println("user: " + user);
        model.addAttribute("user", user);

        model.addAttribute("boardCreatingForm", new BoardCreatingForm());
        model.addAttribute("options", options);

        return "home";
    }

    @PostMapping("create_board")
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

}
