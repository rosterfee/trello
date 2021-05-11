package ru.itis.web.controllers.rest.methods.tasks;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.api.dtos.rest.EntityCreatingReturnDTO;
import ru.itis.api.dtos.rest.ValidationErrorsResponse;
import ru.itis.api.dtos.web.CheckListTaskAddReturnDTO;
import ru.itis.api.dtos.web.CheckListTaskAdditionDTO;
import ru.itis.api.services.CheckListsService;

@RestController
@RequestMapping("api/methods/tasks")
public class ApiTasksController {

    @Autowired
    private CheckListsService checkListsService;



    @ApiOperation(value = "Добавить задачу по id чек-листа")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Задача успешно добавлена",
                    response = EntityCreatingReturnDTO.class),
            @ApiResponse(code = 403, message = "Для выполнения этой операции необходимо " +
                    "обладать правами администратора"),
            @ApiResponse(code = 406, message = "Ощибка валидации",
                    response = ValidationErrorsResponse.class)
    })
    @PostMapping(value = "create_task",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<CheckListTaskAddReturnDTO> addCheckListTask(
            @RequestBody CheckListTaskAdditionDTO additionDTO) {

        Long id = checkListsService.saveTask(additionDTO);
        CheckListTaskAddReturnDTO returnDTO = new CheckListTaskAddReturnDTO(id);

        return ResponseEntity.ok(returnDTO);

    }



    @ApiOperation(value = "Удалить задачу")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Задача успешно удалена"),
            @ApiResponse(code = 403, message = "Для выполнения этой операции необходимо " +
                    "обладать правами администратора"),
            @ApiResponse(code = 404, message = "Задача не найдена")
    })
    @DeleteMapping(value = "delete_task/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<?> deleteTask(@PathVariable("id") Long id) {

        checkListsService.deleteTaskById(id);
        return ResponseEntity.ok().build();

    }

}
