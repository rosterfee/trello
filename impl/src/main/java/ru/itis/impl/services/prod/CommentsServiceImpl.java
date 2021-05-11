package ru.itis.impl.services.prod;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.api.dtos.web.CommentAdditionDTO;
import ru.itis.api.exceptions.ResourceNotFoundException;
import ru.itis.api.services.CommentsService;
import ru.itis.impl.entities.Card;
import ru.itis.impl.entities.Comment;
import ru.itis.impl.entities.User;
import ru.itis.impl.repositories.CardsRepository;
import ru.itis.impl.repositories.CommentsRepository;
import ru.itis.impl.repositories.UsersRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Date saveComment(CommentAdditionDTO commentAdditionDTO) {

        Optional<Card> optionalCard = cardsRepository.findById(
                Long.parseLong(commentAdditionDTO.getCardId()));

//        Optional<User> optionalUser = usersRepository.getByIdOrVkId(
//                Long.parseLong(commentAdditionDTO.getAuthorId()),
//                Long.parseLong(commentAdditionDTO.getAuthorVkId())
//        );

        Optional<User> optionalUser = usersRepository.getById(
                Long.valueOf(commentAdditionDTO.getAuthorId()));

        if (optionalCard.isPresent() && optionalUser.isPresent()) {

            Comment comment = Comment.builder()
                    .card(modelMapper.map(optionalCard.get(), Card.class))
                    .author(modelMapper.map(optionalUser.get(), User.class))
                    .text(commentAdditionDTO.getText())
                    .build();
            commentsRepository.save(comment);

            return new Date();
        }
        else {
            throw new ResourceNotFoundException("Card not found");
        }
    }

}
