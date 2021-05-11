package ru.itis.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.impl.entities.Column;

public interface ColumnsRepository extends JpaRepository<Column, Long> {

    Column getByName(String name);

}
