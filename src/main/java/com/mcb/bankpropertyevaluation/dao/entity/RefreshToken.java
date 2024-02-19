package com.mcb.bankpropertyevaluation.dao.entity;

import javax.persistence.*;
import java.time.Instant;

/**
 * The Class RefreshToken.
 */
@Entity(name = "refreshtoken")
public class RefreshToken {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	/** The user. */
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	/** The token. */
	@Column(nullable = false, unique = true)
	private String token;

	/** The expiry date. */
	@Column(nullable = false)
	private Instant expiryDate;

	/**
	 * Instantiates a new refresh token.
	 */
	public RefreshToken() {
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sets the token.
	 *
	 * @param token the new token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Gets the expiry date.
	 *
	 * @return the expiry date
	 */
	public Instant getExpiryDate() {
		return expiryDate;
	}

	/**
	 * Sets the expiry date.
	 *
	 * @param expiryDate the new expiry date
	 */
	public void setExpiryDate(Instant expiryDate) {
		this.expiryDate = expiryDate;
	}

}