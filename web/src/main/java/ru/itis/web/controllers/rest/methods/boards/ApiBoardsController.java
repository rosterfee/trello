package ru.itis.web.controllers.rest.methods.boards;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itis.api.dtos.rest.EntityCreatingReturnDTO;
import ru.itis.api.dtos.rest.ValidationErrorsResponse;
import ru.itis.api.dtos.web.BoardCreatingForm;
import ru.itis.api.dtos.web.UserDTO;
import ru.itis.api.exceptions.ValidationException;
import ru.itis.api.services.BoardsService;
import ru.itis.api.validation.ValidationErrors;
import ru.itis.web.utils.ValidationErrorsGenerator;

import javax.validation.Valid;

@RestController
@RequestMapping("api/methods/boards")
public class ApiBoardsController {

    @Autowired
    private BoardsService boardsService;



    @ApiOperation(value = "Создать доску")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Доска успешно создана",
                    response = EntityCreatingReturnDTO.class),
            @ApiResponse(code = 403, message = "Для выполнения этой операции необходимо " +
                    "обладать правами администратора"),
            @ApiResponse(code = 406, message = "Ощибка валидации",
                    response = ValidationErrorsResponse.class)
    })
    @PostMapping(value = "create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<EntityCreatingReturnDTO> crateBoard(
            @Valid @RequestBody BoardCreatingForm boardCreatingForm,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDTO user) {

        if (bindingResult.hasErrors()) {

            ValidationErrorsGenerator generator = new ValidationErrorsGenerator();
            ValidationErrors validationErrors =
                    generator.generateValidationErrors(bindingResult);

            throw new ValidationException("Validation error", validationErrors);
        }
        else {
            Long id = boardsService.saveReturningId(boardCreatingForm, user);
            EntityCreatingReturnDTO returnDTO = new EntityCreatingReturnDTO(id);
            return ResponseEntity.ok(returnDTO);
        }

    }



    @ApiOperation(value = "Удалить доску")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Доска успешно удалена"),
            @ApiResponse(code = 403, message = "Для выполнения этой операции необходимо " +
                    "обладать правами администратора"),
            @ApiResponse(code = 404, message = "Доска не найдена")
    })
    @DeleteMapping(value = "delete/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<?> deleteBoard(@PathVariable("id") Long id) {

        boardsService.deleteById(id);
        return ResponseEntity.ok().build();

    }

}
