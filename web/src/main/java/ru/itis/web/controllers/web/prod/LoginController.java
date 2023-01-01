package ru.itis.web.controllers.web.prod;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("login")
public class LoginController {

    @GetMapping
    public String getLoginPage(@RequestParam(value = "status", required = false) String status,
                               Model model) {
        if (status != null && status.equals("error")) {
            model.addAttribute("error", "error");
        }
        return "login";
    }

}
