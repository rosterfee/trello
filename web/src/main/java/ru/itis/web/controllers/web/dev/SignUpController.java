package ru.itis.web.controllers.web.dev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.api.dtos.web.SignUpForm;
import ru.itis.api.services.SignUpService;
import ru.itis.api.enums.Role;

import javax.validation.Valid;

@Controller
@RequestMapping("sign_up")
@Profile("dev")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @GetMapping
    public String getSignUpPage(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "sign_up";
    }

    @PostMapping
    public String signUp(@Valid SignUpForm signUpForm,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("signUpForm", signUpForm);
            return "sign_up";
        }
        else {
            signUpService.signUpWithRole(signUpForm, Role.USER);
        } return "redirect:login";
    }

}
