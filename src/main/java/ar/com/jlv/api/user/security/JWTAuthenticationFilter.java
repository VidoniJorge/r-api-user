package ar.com.jlv.api.user.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.jlv.api.user.entities.UserEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static ar.com.jlv.api.user.security.SecurityConstants.HEADER_STRING;
import static ar.com.jlv.api.user.security.SecurityConstants.TOKEN_PREFIX;

/**
 * Clase responsable del proceso de autenticación
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(final AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/**
	 * Método donde se analiza las credenciales del usuario y las emitimos al
	 * AuthenticationManager
	 */
	@Override
	public Authentication attemptAuthentication(final HttpServletRequest req, final HttpServletResponse res)
			throws AuthenticationException {
		try {

			final UserEntity creds = new ObjectMapper().readValue(req.getInputStream(), UserEntity.class);

			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(creds.getName(), creds.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Método llamado cuando un usuario inicia sesión con éxito. El método se
	 * utiliza para generar el JWT del usuario conectado.
	 */
	@Override
	protected void successfulAuthentication(final HttpServletRequest req, final HttpServletResponse res,
			final FilterChain chain, final Authentication auth) throws IOException, ServletException {
		final String token = JWTUtil.createToken(((User) auth.getPrincipal()).getUsername());
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	}
}
