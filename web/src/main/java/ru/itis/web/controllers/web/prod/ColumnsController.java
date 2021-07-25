package ru.itis.web.controllers.web.prod;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.api.services.ColumnsService;

@Controller
@RequestMapping("columns")
public class ColumnsController {

    private final ColumnsService columnsService;

    public ColumnsController(ColumnsService columnsService) {
        this.columnsService = columnsService;
    }

    @DeleteMapping("delete/{id}")
    public void deleteColumn(@PathVariable("id") Long id) {
        columnsService.deleteById(id);
    }

}
