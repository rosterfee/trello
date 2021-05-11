package ru.itis.web.security.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.impl.entities.RefreshToken;
import ru.itis.impl.repositories.RefreshTokenRepository;

import java.util.Optional;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public Optional<RefreshToken> getByUserId(long userId) {
        return refreshTokenRepository.getByUserId(userId);
    }

    public void deleteById(long id) {
        refreshTokenRepository.deleteById(id);
    }

    public Optional<RefreshToken> getByUUID(String uuid) {
        return refreshTokenRepository.getByUuid(uuid);
    }

    public void delete(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }

}
