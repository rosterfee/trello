package ru.itis.api.services;

import ru.itis.api.dtos.web.CardDTO;
import ru.itis.api.exceptions.ResourceNotFoundException;

import java.util.Optional;

public interface CardsService {

    Optional<Long> save(String title, long columnId);

    Optional<CardDTO> getById(Long id);

    void saveDescription(String text, Long cardId) throws ResourceNotFoundException;

    void delete(Long id);

    void moveCard(Long columnId, Long cardId);

}
