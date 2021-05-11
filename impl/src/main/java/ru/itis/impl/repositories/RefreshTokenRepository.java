package ru.itis.impl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.impl.entities.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> getByUserId(long userId);

    Optional<RefreshToken> getByUuid(String uuid);

}
