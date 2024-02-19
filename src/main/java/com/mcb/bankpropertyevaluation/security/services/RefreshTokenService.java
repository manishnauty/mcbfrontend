package com.mcb.bankpropertyevaluation.security.services;

import com.mcb.bankpropertyevaluation.dao.entity.RefreshToken;
import com.mcb.bankpropertyevaluation.dao.repository.RefreshTokenRepository;
import com.mcb.bankpropertyevaluation.dao.repository.UserRepository;
import com.mcb.bankpropertyevaluation.exception.TokenRefreshException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * The Class RefreshTokenService.
 */
@Service
public class RefreshTokenService {
	
	/** The refresh token duration ms. */
	@Value("${app.jwtRefreshExpirationMs}")
	private Long refreshTokenDurationMs;

	/** The refresh token repository. */
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	/** The user repository. */
	@Autowired
	private UserRepository userRepository;

	/**
	 * Find by token.
	 *
	 * @param token the token
	 * @return the optional
	 */
	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	/**
	 * Creates the refresh token.
	 *
	 * @param userId the user id
	 * @return the refresh token
	 */
	public RefreshToken createRefreshToken(String userId) {
		RefreshToken refreshToken = new RefreshToken();

		refreshToken.setUser(userRepository.findById(userId).get());
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
		refreshToken.setToken(UUID.randomUUID().toString());

		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}

	/**
	 * Verify expiration.
	 *
	 * @param token the token
	 * @return the refresh token
	 */
	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new TokenRefreshException(token.getToken(),
					"Refresh token was expired. Please make a new signin request");
		}

		return token;
	}

	/**
	 * Delete by user id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	@Transactional
	public int deleteByUserId(String userId) {
		return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
	}
}
