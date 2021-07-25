package ru.itis.impl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.api.exceptions.ResourceNotFoundException;
import ru.itis.api.services.ColumnsService;
import ru.itis.impl.entities.Board;
import ru.itis.impl.entities.Column;
import ru.itis.impl.repositories.BoardsRepository;
import ru.itis.impl.repositories.ColumnsRepository;

import java.util.Optional;

@Service
public class ColumnsServiceImpl implements ColumnsService {

    @Autowired
    private ColumnsRepository columnsRepository;

    @Autowired
    private BoardsRepository boardsRepository;

    @Override
    public Long addColumn(String name, Long boardId) {

        Optional<Board> optionalBoard = boardsRepository.findById(boardId);
        if (optionalBoard.isPresent()) {
            Column column = Column.builder()
                    .board(optionalBoard.get())
                    .name(name)
                    .build();
            columnsRepository.save(column);
        } else throw new ResourceNotFoundException("Board not found");

        Column newColumn = columnsRepository.getByName(name);
        return newColumn.getId();
    }

    @Override
    public void deleteById(Long id) {
        columnsRepository.deleteById(id);
    }
}
