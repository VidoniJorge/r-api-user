package ar.com.jlv.api.user.security;

import static ar.com.jlv.api.user.security.SecurityConstants.EXPIRATION_TIME;
import static ar.com.jlv.api.user.security.SecurityConstants.SECRET;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.util.Date;

import com.auth0.jwt.JWT;

public class JWTUtil {

	public static String createToken(final String name) {
		return JWT.create().withSubject(name).withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.sign(HMAC512(SECRET.getBytes()));
	}
}
