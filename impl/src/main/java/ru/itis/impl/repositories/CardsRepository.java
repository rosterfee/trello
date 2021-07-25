package ru.itis.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.impl.entities.Card;

import javax.transaction.Transactional;


public interface CardsRepository extends JpaRepository<Card, Long> {

    @Query(value = "select id from Card card where card.title = :title",
            nativeQuery = true)
    Long getIdByTitle(@Param("title") String title);

    @Transactional
    @Modifying
    @Query(value = "update card set column_id = :columnId where id = :cardId",
            nativeQuery = true)
    public void setCardNewColumnId(@Param("columnId") Long columnId,
                              @Param("cardId") Long cardId);

}
