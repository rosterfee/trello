package ru.itis.impl.services.prod;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.api.dtos.web.BoardCreatingForm;
import ru.itis.api.dtos.web.BoardDTO;
import ru.itis.api.dtos.web.UserDTO;
import ru.itis.api.exceptions.NoPrivilegesException;
import ru.itis.api.exceptions.ResourceNotFoundException;
import ru.itis.api.services.BoardsService;
import ru.itis.impl.entities.Board;
import ru.itis.impl.entities.User;
import ru.itis.impl.repositories.BoardsRepository;

import java.util.*;

@Service
public class BoardsServiceImpl implements BoardsService {

    @Autowired
    private BoardsRepository boardsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void save(BoardCreatingForm boardCreatingForm, UserDTO creatorDTO) {

        User creator = modelMapper.map(creatorDTO, User.class);
        Set<User> participants = new HashSet<>();
        participants.add(creator);

        Board board = Board.builder()
                .name(boardCreatingForm.getName())
                .creator(creator)
                .participants(participants)
                    .build();

        creator.getCreatedBoards().add(board);
        creator.getBoards().add(board);

        boardsRepository.save(board);
    }

    @Override
    public Optional<BoardDTO> getByName(String name) {
        BoardDTO boardDTO = null;
        Optional<Board> optionalBoard = boardsRepository.getByName(name);
        if (optionalBoard.isPresent()) {
            boardDTO = modelMapper.map(optionalBoard.get(), BoardDTO.class);
        }
        return Optional.ofNullable(boardDTO);
    }

    @Override
    public Long saveReturningId(BoardCreatingForm boardCreatingForm, UserDTO creatorDTO) {
        save(boardCreatingForm, creatorDTO);
        return boardsRepository.getByName(boardCreatingForm.getName()).get().getId();
    }

    @Override
    public void deleteById(Long id) {
        boardsRepository.deleteById(id);
    }

}
