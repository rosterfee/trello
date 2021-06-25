package ru.itis.api.services;

import ru.itis.api.dtos.web.BoardCreatingForm;
import ru.itis.api.dtos.web.BoardDTO;
import ru.itis.api.dtos.web.UserDTO;

import java.util.Optional;

public interface BoardsService {

    void save(BoardCreatingForm boardCreatingForm, UserDTO creatorDTO);

    Optional<BoardDTO> getByName(String name);

    Long saveReturningId(BoardCreatingForm boardCreatingForm, UserDTO creatorDTO);

    void deleteById(Long id);

    void addBoardParticipant(BoardDTO board, UserDTO user);

}
