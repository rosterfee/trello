package ru.itis.web.controllers.web.prod;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itis.api.dtos.web.ColumnCreatingDto;
import ru.itis.api.dtos.web.ColumnCreatingReturnDTO;
import ru.itis.api.services.ColumnsService;

@Controller
@RequestMapping("columns")
public class ColumnsController {

    private final ColumnsService columnsService;

    public ColumnsController(ColumnsService columnsService) {
        this.columnsService = columnsService;
    }

    @ResponseBody
    @PostMapping(value = "add")
    public ResponseEntity<ColumnCreatingReturnDTO> addColumn(
            @RequestBody ColumnCreatingDto columnCreatingDto) {

        String name = columnCreatingDto.getName();
        Long boardId = columnCreatingDto.getBoardId();

        Long id = columnsService.addColumn(name, boardId);
        ColumnCreatingReturnDTO returnDto = new ColumnCreatingReturnDTO(id);

        return ResponseEntity.ok(returnDto);
    }

    @DeleteMapping("delete/{id}")
    public void deleteColumn(@PathVariable("id") Long id) {
        columnsService.deleteById(id);
    }

}
