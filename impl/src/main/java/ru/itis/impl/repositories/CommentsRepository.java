package ru.itis.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.impl.entities.Comment;

import javax.transaction.Transactional;

@Transactional
public interface CommentsRepository extends JpaRepository<Comment, Long> {

    @Modifying
    @Query("update Comment comment set comment.text = :text where id = :id")
    @Transactional
    void changeComment(@Param("text") String text,
                       @Param("id") Long id);

}
