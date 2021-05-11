package ru.itis.web.security.web.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.itis.api.dtos.web.UserDTO;
import ru.itis.api.dtos.web.VkAccessTokenData;
import ru.itis.api.dtos.web.VkUserData;
import ru.itis.api.enums.AccountStatus;
import ru.itis.api.enums.Role;
import ru.itis.impl.entities.User;
import ru.itis.impl.properties.VkAppProperties;
import ru.itis.impl.repositories.UsersRepository;
import ru.itis.impl.utils.UserInitialsGenerator;
import ru.itis.web.security.web.authentications.VkOauthAuthentication;

@Service
public class VkOauthService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private VkAppProperties vkAppProperties;

    @Autowired
    private UsersRepository usersRepository;

    public UserDTO getUserInfo(String code) {

        String tokenReceiveUrl = vkAppProperties.getToken_receive_url() + "?client_id="
                + vkAppProperties.getId() + "&client_secret=" + vkAppProperties.getSecret()
                + "&redirect_uri=" + vkAppProperties.getRedirect_uri() + "&code=" + code;

        RestTemplate restTemplate = new RestTemplate();
        VkAccessTokenData tokenData = restTemplate.getForEntity(tokenReceiveUrl,
                VkAccessTokenData.class).getBody();

        String accessToken = tokenData.getAccess_token();
        int id = tokenData.getUser_id();

        String userInfoReceiveUrl = "https://api.vk.com/method/users.get?user_ids=" +
                id + "&fields=photo_50&access_token=" + accessToken + "&v=5.130";

        String jsonUserData = restTemplate.getForEntity(userInfoReceiveUrl, String.class)
                .getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        VkUserData userData;
        try {
            jsonNode = objectMapper.readValue(jsonUserData, JsonNode.class);
            userData = objectMapper.readValue(jsonNode
                            .get("response")
                            .get(0)
                            .toPrettyString(),
                    VkUserData.class);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
        String name = userData.getFirst_name() + " " + userData.getLast_name();
        return UserDTO.builder()
                .vkId((long) id)
                .email(tokenData.getEmail())
                .name(name)
                .initials(UserInitialsGenerator.generate(name))
                .avatar(userData.getPhoto_50())
                .role(Role.USER)
                .accountStatus(AccountStatus.CONFIRMED)
                .build();
    }

    public void authenticate(String email) {
        VkOauthAuthentication authentication = new VkOauthAuthentication();
        authentication.setEmail(email);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("authentication: " + authentication);
    }

    public void saveOrUpdateUser(UserDTO userDTO) {
        if (usersRepository.existsByVkId(userDTO.getVkId())) {
            usersRepository.updateUserByVkId(userDTO.getName(), userDTO.getEmail(),
                    userDTO.getVkId());
        }
        else {
            usersRepository.save(modelMapper.map(userDTO, User.class));
        }
    }

}
