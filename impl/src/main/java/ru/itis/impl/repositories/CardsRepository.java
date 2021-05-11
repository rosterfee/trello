package ru.itis.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.impl.entities.Card;



public interface CardsRepository extends JpaRepository<Card, Long> {

    @Query(value = "select id from Card card where card.title = :title",
            nativeQuery = true)
    Long getIdByTitle(@Param("title") String title);

}
