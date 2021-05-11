package ru.itis.api.services;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID>{

    void save(T t);
    List<T> getAll();
    Optional<T> getOneById(ID id);
    void deleteAll();
    void deleteOneById();

}
