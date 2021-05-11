package ru.itis.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.impl.entities.CheckListTask;

public interface CheckListTasksRepository extends JpaRepository<CheckListTask, Long> {

    CheckListTask getByTitle(String title);

}
