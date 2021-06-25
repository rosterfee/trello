package ru.itis.api.services;

import ru.itis.api.dtos.web.ChangeTaskStatusDTO;
import ru.itis.api.dtos.web.CheckListAdditionDTO;
import ru.itis.api.dtos.web.CheckListTaskAdditionDTO;

public interface CheckListsService {

    Long saveCheckList(CheckListAdditionDTO additionDTO);

    Long saveTask(CheckListTaskAdditionDTO additionDTO);

    void changeTaskStatus(ChangeTaskStatusDTO changeTaskStatusDTO);

    void deleteTaskById(Long id);

    void deleteById(Long id);

}
