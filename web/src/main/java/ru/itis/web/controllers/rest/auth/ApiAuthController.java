package ru.itis.web.controllers.rest.auth;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.itis.api.dtos.rest.ValidationErrorsResponse;
import ru.itis.api.dtos.web.JwtTokens;
import ru.itis.api.dtos.web.LoginFormDTO;
import ru.itis.api.dtos.web.SignUpForm;
import ru.itis.api.dtos.web.UserDTO;
import ru.itis.api.enums.Role;
import ru.itis.api.exceptions.ValidationException;
import ru.itis.api.services.SignUpService;
import ru.itis.api.services.UsersService;
import ru.itis.web.security.api.services.JwtAuthenticationService;
import ru.itis.web.utils.ValidationErrorsGenerator;
import ru.itis.api.validation.ValidationErrors;

import javax.naming.AuthenticationException;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("api/auth")
public class ApiAuthController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private JwtAuthenticationService jwtAuthenticationService;



    @ApiOperation(value = "Зарегистрировать пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь успешно зарегистрирован"),
            @ApiResponse(code = 406, message = "Ошибка валидации",
                response = ValidationErrorsResponse.class)
    })
    @PostMapping(value = "sign_up",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<ValidationErrors> signUpUser(
            @Valid @RequestBody SignUpForm form,
            BindingResult bindingResult) {

        ValidationErrors validationErrors = null;

        if (bindingResult.hasErrors()) {
            ValidationErrorsGenerator generator = new ValidationErrorsGenerator();
            validationErrors = generator.generateValidationErrors(bindingResult);
        }

        if (signUpService.userWithSuchEmailExists(form.getEmail())) {
            if (validationErrors == null) {
                validationErrors = new ValidationErrors(new HashMap<>(), new ArrayList<>());
            }
            validationErrors.getGlobalErrors().add("User with such email already exists");
        }

        if (validationErrors == null) {
            signUpService.signUpWithRole(form, Role.USER);
            return ResponseEntity.ok().build();
        }
        else throw new ValidationException("Ошибка валидации", validationErrors);

    }



    @ApiOperation(value = "Аутентифицироваться с помощью нашего сервиса в вашем приложении")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь успешно аутентифицирован. " +
                    "В качестве ответа вы получите два токена: access и refresh",
            response = JwtTokens.class),
            @ApiResponse(code = 401, message = "Неверные email или пароль")
    })
    @PostMapping(value = "login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<JwtTokens> authenticate(@RequestBody LoginFormDTO loginFormDto) throws AuthenticationException {

        Optional<UserDTO> optionalUserDto = usersService.getUserByEmailAndPassword(
                loginFormDto.getEmail(),
                loginFormDto.getPassword());

        if (optionalUserDto.isPresent()) {
            JwtTokens jwtTokens = jwtAuthenticationService.createTokens(optionalUserDto.get());
            return ResponseEntity.ok(jwtTokens);
        }
        else throw new AuthenticationException("Неверные email или пароль");
    }

}
