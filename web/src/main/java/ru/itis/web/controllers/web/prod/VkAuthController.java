package ru.itis.web.controllers.web.prod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.api.dtos.web.UserDTO;
import ru.itis.web.security.web.services.VkOauthService;

@Controller
@RequestMapping("oauth/vk")
public class VkAuthController {

    @Autowired
    private VkOauthService vkOauthService;

    @GetMapping
    public String authUser(@RequestParam("code") String code) {

        UserDTO userDTO = vkOauthService.getUserInfo(code);
        vkOauthService.saveOrUpdateUser(userDTO);
        vkOauthService.authenticate(userDTO.getEmail());

        return "redirect:/home";
    }

}
