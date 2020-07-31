package ar.com.jlv.api.user.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ar.com.jlv.api.user.entities.UserEntity;
import ar.com.jlv.api.user.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

	private static final String USERNAME = "pedro";
	private static final String PASSWORD = "$2a$10$DE1330J9qm";
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	private UserEntity user;
	
	@BeforeEach
	public void config() {
		this.user = new UserEntity();
		this.user.setName(USERNAME);
		this.user.setPassword(PASSWORD);
	}
	
	@Test
	void testLoadUserByUsername() {
		given(this.userRepository.findByNameAndIsActiveTrue(USERNAME)).willReturn(this.user);
		final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(USERNAME);
		assertEquals(USERNAME, userDetails.getUsername());
		assertEquals(PASSWORD, userDetails.getPassword());
	}

	@Test
	void testLoadUserByUsernameWhenNoExisted() {
		given(this.userRepository.findByNameAndIsActiveTrue(USERNAME)).willReturn(null);
		assertThrows(UsernameNotFoundException.class, ()->userDetailsServiceImpl.loadUserByUsername(USERNAME));
	}
}
