package com.mcb.bankpropertyevaluation.service.impl;


import com.mcb.bankpropertyevaluation.controller.payload.request.LoginRequest;
import com.mcb.bankpropertyevaluation.controller.payload.request.TokenRefreshRequest;
import com.mcb.bankpropertyevaluation.controller.payload.request.UserRequestDTO;
import com.mcb.bankpropertyevaluation.controller.payload.response.JwtResponse;
import com.mcb.bankpropertyevaluation.dao.entity.RefreshToken;
import com.mcb.bankpropertyevaluation.dao.entity.Role;
import com.mcb.bankpropertyevaluation.dao.entity.User;
import com.mcb.bankpropertyevaluation.dao.entity.UserRole;
import com.mcb.bankpropertyevaluation.dao.repository.RoleRepository;
import com.mcb.bankpropertyevaluation.dao.repository.UserRepository;
import com.mcb.bankpropertyevaluation.exception.GenericException;
import com.mcb.bankpropertyevaluation.exception.TokenRefreshException;
import com.mcb.bankpropertyevaluation.security.jwt.JwtUtils;
import com.mcb.bankpropertyevaluation.security.services.RefreshTokenService;
import com.mcb.bankpropertyevaluation.security.services.UserDetailsImpl;
import com.mcb.bankpropertyevaluation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * The Class UserServiceImpl.
 */
@Service
public class UserServiceImpl implements UserService {
	
	/** The role repository. */
	@Autowired
	private RoleRepository roleRepository;
	
	/** The user repository. */
	@Autowired
	private UserRepository userRepository;
	
	/** The authentication manager. */
	@Autowired
	private AuthenticationManager authenticationManager;
	
	/** The jwt utils. */
	@Autowired
	private JwtUtils jwtUtils;
	
	/** The refresh token service. */
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	/** The encoder. */
	@Autowired
	PasswordEncoder encoder;

	/**
	 * Register user.
	 *
	 * @param userRequestDTO the user request DTO
	 * @return the string
	 */
	@Override
	@Transactional
	public Long registerUser(@Valid UserRequestDTO userRequestDTO) {
		if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
			throw new GenericException("Username is already taken!", HttpStatus.CONFLICT);
		}
		// Create new user's account
		User user = new User(userRequestDTO.getUsername(), encoder.encode(userRequestDTO.getPassword()),userRequestDTO.getContactNumber(), userRequestDTO.getBuisnessUnit());
		Set<Role> roles = new HashSet<>();

		if (Objects.isNull(userRequestDTO.getRole())) {
			Role userRole = roleRepository.findByName(UserRole.EMPLOYEE)
					.orElseThrow(() -> new GenericException("Role not exist!", HttpStatus.NOT_FOUND));
			roles.add(userRole);
		} else {
			if (userRequestDTO.getRole().equals(UserRole.ADMIN.name())) {
				Role adminRole = roleRepository.findByName(UserRole.ADMIN)
						.orElseThrow(() -> new GenericException("Role not exist", HttpStatus.NOT_FOUND));
				roles.add(adminRole);
			} else if (userRequestDTO.getRole().equals(UserRole.EMPLOYEE.name())) {
				Role adminRole = roleRepository.findByName(UserRole.EMPLOYEE)
						.orElseThrow(() -> new GenericException("Role not exist", HttpStatus.NOT_FOUND));
				roles.add(adminRole);
			} else {
				throw new GenericException("Role not exist!", HttpStatus.NOT_FOUND);
			}
		}
		user.setRoles(roles);
		userRepository.save(user);
		return user.getId();
	}

	/**
	 * Authenticate user.
	 *
	 * @param loginRequest the login request
	 * @return the jwt response
	 */
	@Override
	public JwtResponse authenticateUser(@Valid LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
		Optional<User> optionalUser = userRepository.findById(userDetails.getId());
		return new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(), userDetails.getUsername(),
				userDetails.getRoleId(), optionalUser.get().getBuisnessUnit());
	}

	/**
	 * Signout.
	 */
	@Override
	public void signout() {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long userId = userDetails.getId();
		refreshTokenService.deleteByUserId(userId);

	}

	@Override
	public JwtResponse refreshToken(@Valid TokenRefreshRequest request) {
		return refreshTokenService.findByToken(request.getRefreshToken())
		        .map(refreshTokenService::verifyExpiration)
		        .map(RefreshToken::getUser)
		        .map(user -> {
		          String token = jwtUtils.generateTokenFromUsername(user.getUsername());
		          Integer roleId = user.getRoles().stream().findFirst().get().getId();
		          return new JwtResponse(token, request.getRefreshToken(), user.getId(), user.getUsername(), roleId);
		        })
		        .orElseThrow(() -> new TokenRefreshException(request.getRefreshToken(),
		            "Refresh token is not in database!"));
	}

}
