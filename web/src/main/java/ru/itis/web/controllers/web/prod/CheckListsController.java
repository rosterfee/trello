package ru.itis.web.controllers.web.prod;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itis.api.dtos.web.*;
import ru.itis.api.services.CheckListsService;

@Controller
@RequestMapping("checklists")
public class CheckListsController {

    private final CheckListsService checkListsService;

    public CheckListsController(CheckListsService checkListsService) {
        this.checkListsService = checkListsService;
    }

    @PostMapping(value = "add",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CheckListAddReturnDTO> addCheckList(
            @RequestBody CheckListAdditionDTO additionDTO) {

        Long id = checkListsService.saveCheckList(additionDTO);
        CheckListAddReturnDTO returnDTO = new CheckListAddReturnDTO(id);
        return ResponseEntity.ok(returnDTO);
    }

    @PostMapping("tasks/add")
    public ResponseEntity<CheckListTaskAddReturnDTO> addCheckListTask(
            @RequestBody CheckListTaskAdditionDTO additionDTO) {

        Long id = checkListsService.saveTask(additionDTO);
        CheckListTaskAddReturnDTO returnDTO = new CheckListTaskAddReturnDTO(id);

        return ResponseEntity.ok(returnDTO);
    }

    @PatchMapping("tasks/changeStatus")
    public ResponseEntity<?> addCheckListTask(@RequestBody ChangeTaskStatusDTO changeTaskStatusDTO) {

        checkListsService.changeTaskStatus(changeTaskStatusDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("remove/{id}")
    public void removeChecklist(@PathVariable("id") Long id) {
        System.out.println("hello");
        checkListsService.deleteById(id);
    }

    @DeleteMapping("tasks/remove/{id}")
    public void removeTask(@PathVariable("id") Long id) {
        checkListsService.deleteTaskById(id);
    }

}
