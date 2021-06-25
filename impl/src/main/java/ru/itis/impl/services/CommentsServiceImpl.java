package ru.itis.impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.api.dtos.web.CommentAdditionDTO;
import ru.itis.api.dtos.web.CommentAdditionReturnDTO;
import ru.itis.api.dtos.web.CommentChangeDTO;
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
    public CommentAdditionReturnDTO saveComment(CommentAdditionDTO commentAdditionDTO) {

        Optional<Card> optionalCard = cardsRepository.findById(
                Long.parseLong(commentAdditionDTO.getCardId()));

        Optional<User> optionalUser = usersRepository.getById(
                Long.valueOf(commentAdditionDTO.getAuthorId()));

        if (optionalCard.isPresent() && optionalUser.isPresent()) {

            Comment comment = Comment.builder()
                    .card(modelMapper.map(optionalCard.get(), Card.class))
                    .author(modelMapper.map(optionalUser.get(), User.class))
                    .text(commentAdditionDTO.getText())
                    .build();
            commentsRepository.save(comment);

            return CommentAdditionReturnDTO.builder()
                    .id(comment.getId())
                    .date(comment.getCreatedAt())
                        .build();
        }
        else {
            throw new ResourceNotFoundException("Card not found");
        }
    }

    @Override
    public void deleteById(Long id) {
        commentsRepository.deleteById(id);
    }

    @Override
    public void changeComment(CommentChangeDTO commentChangeDTO) {

        String text = commentChangeDTO.getText();
        Long id = commentChangeDTO.getId();

        commentsRepository.changeComment(text, id);
    }

}
