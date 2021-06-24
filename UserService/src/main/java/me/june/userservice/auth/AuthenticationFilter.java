package me.june.userservice.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.june.userservice.user.dto.RequestLogin;
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

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(
		HttpServletRequest request,
		HttpServletResponse response
	) throws AuthenticationException {
		try {
			RequestLogin requestLogin = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);
			return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
					requestLogin.getEmail(),
					requestLogin.getPassword(),
					new ArrayList<>()
				)
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain,
		Authentication authResult
	) throws IOException, ServletException {
		User user = (User)authResult.getPrincipal();
		logger.debug(String.format("{}, {}", user.getUsername(), user.getPassword()));
		super.successfulAuthentication(request, response, chain, authResult);
	}
}
