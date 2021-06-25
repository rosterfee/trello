package ru.itis.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.impl.entities.Board;
import ru.itis.impl.entities.User;

import javax.transaction.Transactional;
import java.util.Optional;

public interface BoardsRepository extends JpaRepository<Board, Long> {

    Optional<Board> getByName(String name);

    void deleteById(Long id);
}
