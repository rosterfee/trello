package ru.itis.web.controllers.web.prod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.itis.api.dtos.web.BoardCreatingForm;
import ru.itis.api.dtos.web.UserDTO;
import ru.itis.api.services.BoardsService;
import ru.itis.impl.utils.UserInitialsGenerator;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("home")
public class HomeController {

    @Autowired
    private BoardsService boardsService;

    @GetMapping
    public String BoardsPage(@AuthenticationPrincipal UserDTO user, Model model) {

        model.addAttribute(user);
        model.addAttribute("initials", UserInitialsGenerator.generate(user.getName()));

        return "home";
    }

    @PostMapping("create_board")
    public String createBoard(@Valid @ModelAttribute BoardCreatingForm form,
                              BindingResult bindingResult,
                              Model model,
                              @AuthenticationPrincipal UserDTO user) throws IOException {

        if (bindingResult.hasErrors()) {

            List<String> errors = new ArrayList<>();
            for (FieldError error: bindingResult.getFieldErrors()) {
                errors.add(error.getDefaultMessage());
            }

            model.addAttribute("errors", errors);
            model.addAttribute("initials", UserInitialsGenerator.generate(user.getName()));
            model.addAttribute(user);

            return "home";
        }
        else {
            boardsService.save(form, user);
            return "redirect:/boards/" + form.getName();
        }

    }

}
