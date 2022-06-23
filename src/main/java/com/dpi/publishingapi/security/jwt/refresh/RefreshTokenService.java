package com.dpi.publishingapi.security.jwt.refresh;

import com.dpi.publishingapi.auth.dtos.response.RefreshTokenResponse;
import com.dpi.publishingapi.auth.user.User;
import com.dpi.publishingapi.auth.user.UserRepository;
import com.dpi.publishingapi.exceptions.CustomException;
import com.dpi.publishingapi.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    @Value("${jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository, JwtUtils jwtUtils) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    private Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        String token = UUID.randomUUID().toString();
        Instant expirationDate = Instant.now().plusMillis(refreshTokenDurationMs);
        RefreshToken refreshToken = new RefreshToken(user, token, expirationDate);
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpirationDate().compareTo(Instant.now()) <= 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new CustomException("Refresh token is expired.  You will have to sign in again");
        }
        return refreshToken;
    }

    public RefreshTokenResponse refreshToken(String token) {
        return findByToken(token)
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String newToken = jwtUtils.generateToken(user.getEmail());
                    return new RefreshTokenResponse(newToken, token);
                }).orElseThrow(() -> new CustomException("Refresh token does not exist", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userId);
    }
}
