package ru.itis.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.impl.entities.User;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface UsersRepository extends JpaRepository<User, Long> {

    Optional<User> getByEmail(String email);

    Optional<User> getUserByConfirmCode(String confirmCode);

    boolean existsByVkId(Long vkId);

    boolean existsByEmail(String email);

    @Modifying
    @Query("update User user set user.name = :name, user.email = :email " +
            "where user.vkId = :vkId")
    void updateUserByVkId(@Param("name") String name,
                          @Param("email") String email,
                          @Param("vkId") Long vkId);

    //Optional<User> getByIdOrVkId(Long id, Long vkId);

    Optional<User> getById(Long id);

}
