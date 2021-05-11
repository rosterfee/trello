package ru.itis.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.impl.entities.Comment;

public interface CommentsRepository extends JpaRepository<Comment, Long> {



}
