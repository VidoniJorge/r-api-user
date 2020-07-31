package ar.com.jlv.api.user.services.impl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ar.com.jlv.api.user.dtos.PhoneDTO;
import ar.com.jlv.api.user.dtos.UserDTO;
import ar.com.jlv.api.user.entities.PhoneEntity;
import ar.com.jlv.api.user.entities.UserEntity;
import ar.com.jlv.api.user.mappers.PhoneMapper;
import ar.com.jlv.api.user.mappers.UserMapper;
import ar.com.jlv.api.user.model.PhoneConst;
import ar.com.jlv.api.user.model.UserConst;
import ar.com.jlv.api.user.repositories.UserRepository;
import ar.com.jlv.api.user.security.JWTUtil;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private UserMapper userMapper;
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Mock
	private PhoneMapper phoneMapper;

	@InjectMocks
	private UserServiceImpl userService;

	private static LocalDateTime creationDate;
	private UserDTO userDTO;
	private PhoneDTO phoneDTO;
	private UserEntity userEntity;
	private PhoneEntity phoneEntity;

	@BeforeAll
	public static void init() {
		creationDate = LocalDateTime.now();
	}

	@BeforeEach
	public void config() {
		this.phoneEntity = PhoneEntity.builder().number(PhoneConst.NUMBER).countryCode(PhoneConst.COUNTRY_CODE)
				.cityCode(PhoneConst.CITY_CODE).build();
		this.userEntity = UserEntity.builder().id(UserConst.ID).name(UserConst.USERNAME).email(UserConst.EMAIL)
				.password(UserConst.PASSWORD).phones(List.of(this.phoneEntity)).create(creationDate)
				.lastLogin(creationDate).isActive(true).build();

		this.phoneDTO = PhoneDTO.builder().number(PhoneConst.NUMBER).countryCode(PhoneConst.COUNTRY_CODE)
				.cityCode(PhoneConst.CITY_CODE).build();
		this.userDTO = UserDTO.builder().id(UserConst.ID).name(UserConst.USERNAME).email(UserConst.EMAIL)
				.password(UserConst.PASSWORD).phones(List.of(this.phoneDTO)).create(creationDate)
				.lastLogin(creationDate).isActive(true).build();
	}

	@Test
	void testFindAll() {
		given(this.userRepository.findAll()).willReturn(List.of(this.userEntity));
		given(this.userMapper.convertEntityToDto(this.userEntity)).willReturn(this.userDTO);
		final List<UserDTO> users = this.userService.findAll();
		assertEquals(List.of(this.userDTO), users);
	}

	@Test
	void testFindById() {
		given(this.userRepository.findById(UserConst.ID)).willReturn(Optional.of(this.userEntity));
		given(this.userMapper.convertEntityToDto(this.userEntity)).willReturn(this.userDTO);
		final UserDTO user = this.userService.findById(UserConst.ID);
		assertEquals(this.userDTO, user);
	}

	@Test
	void testCreate() {
		given(this.userMapper.convertDtoToEntity(this.userDTO)).willReturn(this.userEntity);
		
		doReturn(this.userEntity).when(this.userRepository).save(any(UserEntity.class));
		
		given(this.userMapper.convertEntityToDto(this.userEntity)).willReturn(this.userDTO);
		
		this.userEntity.setPassword(JWTUtil.createToken(this.userDTO.getName()));
		final UserDTO user = this.userService.create(this.userDTO);
		
		assertEquals(this.userDTO, user);
	}

	@Test
	void testUpdate() {
		given(this.userRepository.findById(UserConst.ID)).willReturn(Optional.of(this.userEntity));
		final String passEncyp = "998877"; 
		given(this.bCryptPasswordEncoder.encode(userDTO.getPassword())).willReturn(passEncyp);
		this.userEntity.setPassword(passEncyp);
		this.userService.update(UserConst.ID, this.userDTO);
		
		verify(this.userRepository).save(this.userEntity);
	}

	@Test
	void testDelete() {
		given(this.userRepository.findById(UserConst.ID)).willReturn(Optional.of(this.userEntity));
		this.userEntity.setIsActive(false);
		this.userService.delete(UserConst.ID);
		verify(this.userRepository).save(this.userEntity);
	}

}
