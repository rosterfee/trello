package ru.itis.api.dtos.web;

import lombok.Data;

@Data
public class VkAccessTokenData {

    private String access_token;
    private String expires_in;
    private int user_id;
    private String email;

}
