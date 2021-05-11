package ru.itis.api.services;

import ru.itis.api.dtos.web.UserDTO;

public interface RefreshPrincipalService {

    void refreshPrincipal(UserDTO userDTO);

}
