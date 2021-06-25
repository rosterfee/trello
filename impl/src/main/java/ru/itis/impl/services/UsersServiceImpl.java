package ru.itis.impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.api.dtos.web.UserDTO;
import ru.itis.api.exceptions.ResourceNotFoundException;
import ru.itis.api.services.UsersService;
import ru.itis.impl.entities.User;
import ru.itis.impl.repositories.UsersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO getById(Long id) {
        Optional<User> optionalUser = usersRepository.findById(id);
        if (optionalUser.isPresent()) {
            return modelMapper.map(optionalUser.get(), UserDTO.class);
        } else throw new ResourceNotFoundException("User not found");
    }

    @Override
    public Optional<UserDTO> getUserByEmailAndPassword(String email, String password) {

        UserDTO userDto = null;

        Optional<User> optionalUser = usersRepository.getByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getHashPassword())) {
                userDto = modelMapper.map(user, UserDTO.class);
            }
        }

        return Optional.ofNullable(userDto);

    }

    @Override
    public List<UserDTO> getAllByEmailContaining(String email) {
        List<User> users = usersRepository.getAllByEmailContaining(email);
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user: users) {
            userDTOS.add(modelMapper.map(user, UserDTO.class));
        }
        System.out.println(userDTOS);
        return userDTOS;
    }
}
