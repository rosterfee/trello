package ru.itis.web.controllers.web.prod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itis.api.dtos.web.SignUpForm;
import ru.itis.api.enums.Role;
import ru.itis.api.exceptions.UserNotFoundException;
import ru.itis.api.services.SignUpService;

import javax.validation.Valid;

@Profile("prod")
@Controller
@RequestMapping("sign_up")
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
            return "redirect:/sign_up/pls_confirm_email";
        }
    }

    @GetMapping("pls_confirm_email")
    public String getEmailConfirmationPage() {
        return "confirm_email";
    }

    @GetMapping("confirm_email/{confirm_code}")
    public String confirmEmail(@PathVariable("confirm_code") String confirmCode) throws UserNotFoundException {
        signUpService.confirmUserByConfirmCode(confirmCode);
        return "email_confirmed";
    }

}
