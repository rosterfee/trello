package ru.itis.api.dtos.web;

import lombok.Data;

@Data
public class VkUserData {

    private Integer id;
    private String first_name;
    private String last_name;
    private Boolean is_closed;
    private Boolean can_access_closed;
    private String photo_50;
}
