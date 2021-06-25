package ru.itis.impl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.api.dtos.web.ChangeTaskStatusDTO;
import ru.itis.api.dtos.web.CheckListAdditionDTO;
import ru.itis.api.dtos.web.CheckListTaskAdditionDTO;
import ru.itis.api.exceptions.ResourceNotFoundException;
import ru.itis.api.services.CheckListsService;
import ru.itis.impl.entities.Card;
import ru.itis.impl.entities.CheckList;
import ru.itis.impl.entities.CheckListTask;
import ru.itis.impl.repositories.CardsRepository;
import ru.itis.impl.repositories.CheckListTasksRepository;
import ru.itis.impl.repositories.CheckListsRepository;

import java.util.Optional;

@Service
public class CheckListsServiceImpl implements CheckListsService {

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private CheckListsRepository checkListsRepository;

    @Autowired
    private CheckListTasksRepository checkListTasksRepository;

    @Override
    public Long saveCheckList(CheckListAdditionDTO additionDTO) {

        Optional<Card> optionalCard = cardsRepository.findById(
                Long.parseLong(additionDTO.getCardId()));

        if (optionalCard.isPresent()) {
            CheckList checkList = CheckList.builder()
                    .title(additionDTO.getText())
                    .card(optionalCard.get())
                        .build();

            checkListsRepository.save(checkList);
            CheckList newCheckList = checkListsRepository
                    .getByTitle(checkList.getTitle());
            return newCheckList.getId();
        }
        else throw new ResourceNotFoundException("Card not found");
    }

    @Override
    public Long saveTask(CheckListTaskAdditionDTO additionDTO) {

        Optional<CheckList> optionalCheckList = checkListsRepository
                .findById(Long.valueOf(additionDTO.getCheckListId()));

        if (optionalCheckList.isPresent()) {
            CheckListTask checkListTask = CheckListTask.builder()
                    .title(additionDTO.getTitle())
                    .done(false)
                    .checkList(optionalCheckList.get())
                        .build();
            checkListTasksRepository.save(checkListTask);

            CheckListTask newTask = checkListTasksRepository
                    .getByTitle(checkListTask.getTitle());
            return newTask.getId();

        }
        else throw new ResourceNotFoundException("Checklist not found");
    }

    @Override
    public void changeTaskStatus(ChangeTaskStatusDTO changeTaskStatusDTO) {

        Optional<CheckListTask> optionalTask = checkListTasksRepository
                .findById(Long.valueOf(changeTaskStatusDTO.getTaskId()));

        if (optionalTask.isPresent()) {
            CheckListTask task = optionalTask.get();
            task.setDone(changeTaskStatusDTO.isDone());
            checkListTasksRepository.save(task);
        } else throw new ResourceNotFoundException("Task not found");

    }

    @Override
    public void deleteTaskById(Long id) {
        checkListTasksRepository.deleteById(id);
    }

    @Override
    public void deleteById(Long id) {
        checkListsRepository.deleteById(id);
    }

}
