package ru.itis.impl.services.prod;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.itis.api.dtos.web.CardDTO;
import ru.itis.api.exceptions.ResourceNotFoundException;
import ru.itis.api.services.CardsService;
import ru.itis.impl.entities.Card;
import ru.itis.impl.entities.Column;
import ru.itis.impl.repositories.CardsRepository;
import ru.itis.impl.repositories.ColumnsRepository;

import java.util.Optional;

@Service
public class CardsServiceImpl implements CardsService {

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private ColumnsRepository columnsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<Long> save(String title, long columnId) {

        Long id = null;

        Optional<Column> optionalColumn = columnsRepository.findById(columnId);
        if (optionalColumn.isPresent()) {
            Card card = Card.builder()
                    .title(title)
                    .column(optionalColumn.get())
                        .build();
            cardsRepository.save(card);
            id = cardsRepository.getIdByTitle(title);

        }
        return Optional.ofNullable(id);
    }

    @Override
    public Optional<CardDTO> getById(Long id) {

        CardDTO cardDTO = null;

        Optional<Card> optionalCard = cardsRepository.findById(id);
        if (optionalCard.isPresent()) {
            cardDTO = modelMapper.map(optionalCard.get(), CardDTO.class);
        }

        return Optional.ofNullable(cardDTO);

    }

    @Override
    public void saveDescription(String text, Long cardId) throws ResourceNotFoundException {

        Optional<Card> optionalCard = cardsRepository.findById(cardId);
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            card.setDescription(text);
            cardsRepository.save(card);
        }
        else {
            throw new ResourceNotFoundException("Card id is wrong");
        }
    }

}
