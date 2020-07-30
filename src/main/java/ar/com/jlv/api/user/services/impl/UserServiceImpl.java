package ar.com.jlv.api.user.services.impl;

import static ar.com.jlv.api.user.security.SecurityConstants.EXPIRATION_TIME;
import static ar.com.jlv.api.user.security.SecurityConstants.SECRET;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import ar.com.jlv.api.user.dtos.UserDTO;
import ar.com.jlv.api.user.entities.UserEntity;
import ar.com.jlv.api.user.exceptions.ExistedEmailException;
import ar.com.jlv.api.user.exceptions.UserNotFoundException;
import ar.com.jlv.api.user.mappers.PhoneMapper;
import ar.com.jlv.api.user.mappers.UserMapper;
import ar.com.jlv.api.user.repositories.UserRepository;
import ar.com.jlv.api.user.services.UserService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private UserMapper userMapper;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private PhoneMapper phoneMapper;

	public UserServiceImpl(final UserRepository userRepository, final UserMapper userMapper,
			final BCryptPasswordEncoder bCryptPasswordEncoder, final PhoneMapper phoneMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.phoneMapper = phoneMapper;
	}

	public List<UserDTO> findAll() {
		log.trace("Inicio de la busqueda de usuarios");
		final List<UserDTO> users = this.userRepository.findAll().stream().map(this.userMapper::convertEntityToDto)
				.collect(Collectors.toList());
		log.trace("Fin de la busqueda de usuarios");
		return users;
	}

	public UserDTO findById(final String id) {
		final UserEntity entity = sendFindById(id);
		return this.userMapper.convertEntityToDto(entity);
	}

	public UserDTO create(final UserDTO user) {
		this.validatedNonExistentEmail(user.getEmail());
		final UserEntity entity = userMapper.convertDtoToEntity(user);
		final LocalDateTime creationDate = LocalDateTime.now();
		entity.setCreate(creationDate);
		entity.setCreate(creationDate);
		entity.setIsActive(true);
		entity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		final String token = JWT.create().withSubject(user.getName())
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).sign(HMAC512(SECRET.getBytes()));
		entity.setToken(token);

		log.trace("Inicio del alta de usuario");
		this.userRepository.save(entity);
		log.trace("Fin del alta de usuario");

		return this.userMapper.convertEntityToDto(entity);
	}

	public void update(final String id, final UserDTO user) {
		final UserEntity entity = sendFindById(id);
		entity.setModified(LocalDateTime.now());
		entity.setEmail(user.getEmail());
		entity.setName(user.getName());
		entity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		entity.setPhones(phoneMapper.convertDtosToEntities(user.getPhones()));

		log.trace("Inicio de modificación de usuario");
		this.userRepository.save(entity);
		log.trace("Fin de la mmodificación del usuario");
	}

	public void delete(final String id) {
		final UserEntity entity = sendFindById(id);
		entity.setIsActive(false);

		log.trace("Inicio de la baja del usuario");
		this.userRepository.save(entity);
		log.trace("Fin de la baja del usuario");
	}

	private UserEntity sendFindById(final String id) {
		log.trace("Inicio de la busqueda del usuario");
		final UserEntity user = this.userRepository.findById(id).map(entity -> {
			return entity;
		}).orElseThrow(() -> new UserNotFoundException());
		log.trace("Inicio de la busqueda del usuario");
		return user;
	}

	private void validatedNonExistentEmail(final String email) {
		final Optional<UserEntity> response = this.userRepository.findByEmailIgnoreCase(email);
		if (response.isPresent()) {
			throw new ExistedEmailException();
		}
	}
}
