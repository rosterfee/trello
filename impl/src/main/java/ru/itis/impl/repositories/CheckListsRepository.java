package ru.itis.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.impl.entities.CheckList;

public interface CheckListsRepository extends JpaRepository<CheckList, Long> {

    CheckList getByTitle(String title);

}
