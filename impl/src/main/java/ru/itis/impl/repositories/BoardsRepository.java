package ru.itis.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.impl.entities.Board;

import java.util.Optional;

public interface BoardsRepository extends JpaRepository<Board, Long> {

    Optional<Board> getByName(String name);

    void deleteById(Long id);

}
