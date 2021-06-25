package ru.itis.api.services;

import ru.itis.api.dtos.web.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UsersService {

    UserDTO getById(Long id);

    Optional<UserDTO> getUserByEmailAndPassword(String email, String password);

    List<UserDTO> getAllByEmailContaining(String email);

}
