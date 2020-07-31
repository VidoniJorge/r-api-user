package ar.com.jlv.api.user.controllers.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ar.com.jlv.api.user.dtos.PhoneDTO;
import ar.com.jlv.api.user.dtos.UserDTO;
import ar.com.jlv.api.user.model.PhoneConst;
import ar.com.jlv.api.user.model.UserConst;
import ar.com.jlv.api.user.services.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerImplTest {

	@Mock
	private UserService userService;

	@InjectMocks
	private UserControllerImpl userController;

	private static LocalDateTime creationDate;
	private UserDTO userBase;
	private PhoneDTO phone;

	@BeforeAll
	public static void init() {
		creationDate = LocalDateTime.now();
	}

	@BeforeEach
	public void config() {
		this.phone = PhoneDTO.builder().number(PhoneConst.NUMBER).countryCode(PhoneConst.COUNTRY_CODE)
				.cityCode(PhoneConst.CITY_CODE).build();
		this.userBase = UserDTO.builder().id(UserConst.ID).name(UserConst.USERNAME).email(UserConst.EMAIL)
				.password(UserConst.PASSWORD).phones(List.of(this.phone)).create(creationDate).lastLogin(creationDate)
				.isActive(true).build();
	}

	@Test
	void testFindAll() {

		given(this.userService.findAll()).willReturn(List.of(this.userBase));

		final ResponseEntity<List<UserDTO>> response = userController.findAll();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(List.of(this.userBase), response.getBody());
	}

	@Test
	void testFindById() {
		given(this.userService.findById(UserConst.ID)).willReturn(this.userBase);

		final ResponseEntity<UserDTO> response = userController.findById(UserConst.ID);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(this.userBase, response.getBody());
	}

	@Test
	void testCreate() {
		given(this.userService.create(this.userBase)).willReturn(this.userBase);
		final ResponseEntity<UserDTO> response = userController.create(this.userBase);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(this.userBase, response.getBody());
	}

	@Test
	void testUpdate() {
		this.userController.update(UserConst.ID, this.userBase);
		verify(this.userService).update(UserConst.ID, this.userBase);
	}

	@Test
	void testDelete() {
		this.userController.delete(UserConst.ID);
		verify(this.userService).delete(UserConst.ID);
	}

}
