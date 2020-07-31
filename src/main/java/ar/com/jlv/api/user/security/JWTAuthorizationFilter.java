package ar.com.jlv.api.user.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static ar.com.jlv.api.user.security.SecurityConstants.HEADER_STRING;
import static ar.com.jlv.api.user.security.SecurityConstants.SECRET;
import static ar.com.jlv.api.user.security.SecurityConstants.TOKEN_PREFIX;

/**
 * Clase responsable de de la autorizacón del usuario.
 *
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	private UserDetailsService userDetailsService;

	public JWTAuthorizationFilter(final AuthenticationManager authManager,
			final UserDetailsService userDetailsService) {
		super(authManager);
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		final String header = req.getHeader(HEADER_STRING);

		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}

		final String userName = this.getUser(req);

		this.userDetailsService.loadUserByUsername(userName);

		final UsernamePasswordAuthenticationToken authentication = getAuthentication(userName);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	/**
	 * Método que valida el token enviado en el header.
	 * 
	 * @param request
	 * @return
	 */
	private UsernamePasswordAuthenticationToken getAuthentication(final String user) {
		if (Objects.nonNull(user)) {
			return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
		}
		return null;
	}

	private String getUser(final HttpServletRequest request) {
		final String token = request.getHeader(HEADER_STRING);
		if (Objects.nonNull(token)) {
			return JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build().verify(token.replace(TOKEN_PREFIX, ""))
					.getSubject();
		}
		return null;
	}
}
