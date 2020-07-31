package ar.com.jlv.api.user.services.impl;

import java.util.Objects;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.com.jlv.api.user.entities.UserEntity;
import ar.com.jlv.api.user.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

import static java.util.Collections.emptyList;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;

	public UserDetailsServiceImpl(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Inicio de la búsqueda de usuario activo por nombre");
		final UserEntity user = userRepository.findByNameAndIsActiveTrue(username);
		log.info("Fin de la búsqueda de usuario activo por nombre");

		if (Objects.isNull(user)) {
			throw new UsernameNotFoundException(username);
		}

		return new User(user.getName(), user.getPassword(), emptyList());
	}

}
