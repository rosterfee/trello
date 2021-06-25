package ru.itis.impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.api.dtos.web.SignUpForm;
import ru.itis.api.enums.AccountStatus;
import ru.itis.api.enums.Role;
import ru.itis.api.services.EmailSendingService;
import ru.itis.api.services.SignUpService;
import ru.itis.impl.entities.User;
import ru.itis.api.exceptions.UserNotFoundException;
import ru.itis.impl.repositories.UsersRepository;
import ru.itis.impl.utils.ConfirmMailGenerator;
import ru.itis.impl.utils.UserInitialsGenerator;

import java.util.UUID;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailSendingService emailSendingService;

    @Autowired
    private ConfirmMailGenerator confirmMailGenerator;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Override
    public void signUpWithRole(SignUpForm signUpForm, Role role) {

        User user = User.builder()
                .name(signUpForm.getName())
                .initials(UserInitialsGenerator.generate(signUpForm.getName()))
                .email(signUpForm.getEmail())
                .hashPassword(passwordEncoder.encode(signUpForm.getPassword()))
                .role(role)
                .confirmCode(UUID.randomUUID().toString())
                .build();

        AccountStatus accountStatus;
        if (activeProfile.equals("dev")) {
            accountStatus = AccountStatus.CONFIRMED;
        } else accountStatus = AccountStatus.NOT_CONFIRMED;

        user.setAccountStatus(accountStatus);
        usersRepository.save(user);

        if (activeProfile.equals("prod")) {
            String letter = confirmMailGenerator.generateConfirmMail(
                    user.getConfirmCode(),
                    user.getName()
            );
            emailSendingService.sendEmail(user.getEmail(), letter,
                    "Подтверждение email");
        }
    }

    @Override
    public void confirmUserByConfirmCode(String confirmCode) throws UserNotFoundException {

        User user = usersRepository.getUserByConfirmCode(confirmCode)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setAccountStatus(AccountStatus.CONFIRMED);
        usersRepository.save(user);
    }

    @Override
    public boolean userWithSuchEmailExists(String email) {
        return usersRepository.existsByEmail(email);
    }

}
