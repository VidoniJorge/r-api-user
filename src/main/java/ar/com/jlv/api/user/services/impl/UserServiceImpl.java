package ar.com.jlv.api.user.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ar.com.jlv.api.user.dtos.UserDTO;
import ar.com.jlv.api.user.entities.UserEntity;
import ar.com.jlv.api.user.exceptions.UserNotFoundException;
import ar.com.jlv.api.user.mappers.UserMapper;
import ar.com.jlv.api.user.repositories.UserRepository;
import ar.com.jlv.api.user.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private UserMapper userMapper;

	public UserServiceImpl(final UserRepository userRepository, final UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	public List<UserDTO> findAll() {
		return this.userRepository.findAll().stream().map(this.userMapper::convertEntityToDto)
				.collect(Collectors.toList());
	}

	public UserDTO findById(final String id) {
		final UserEntity entity = sendFindById(id);
		return this.userMapper.convertEntityToDto(entity);
	}

	public void create(final UserDTO user) {
		this.validatedNonExistentEmail(user.getEmail());
		final UserEntity entity = userMapper.convertDtoToEntity(user);
		entity.setCreate(LocalDateTime.now());
		entity.setIsActive(true);
		this.userRepository.save(entity);
	}

	public void update(final String id, final UserDTO user) {
		final UserEntity entity = sendFindById(id);
		entity.setModified(LocalDateTime.now());
		entity.setEmail(user.getEmail());
		entity.setName(user.getName());
		entity.setPassword(user.getPassword());
		//TODO agreager lo del telefono, en este momento quiero avanzar con otra cosa
		//entity.setPhones(user.getPhones());
	}

	public void delete(final String id) {
		final UserEntity entity = sendFindById(id);

		this.userRepository.save(entity);
	}

	private UserEntity sendFindById(final String id) {
		return this.userRepository.findById(id).map(entity -> {
			entity.setIsActive(false);
			return entity;
		}).orElseThrow(() -> new UserNotFoundException());
	}
	
	private void validatedNonExistentEmail(final String email) {
		Optional response = this.userRepository.findByEmailIgnoreCase(email);
		if(response.isPresent()) {
			System.out.println("siiiiiiii");
		} else {
			System.out.println("noooooo");
		}
			
	}
}
