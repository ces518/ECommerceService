package me.june.apigatewayservice.filter;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
	private final Environment env;

	public AuthorizationHeaderFilter(Environment env) {
		super(Config.class);
		this.env = env;
	}

	// login -> token -> users (with token) -> header(include token)
	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();

			// Authorization Header 가 없음
			if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return onError(exchange, "no authorization header", HttpStatus.UNAUTHORIZED);
			}

			String authHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			String jwt = authHeader.replace("Bearer ", "");

			if (!isJwtValid(jwt)) {
				return onError(exchange, "JWT Token is not valid", HttpStatus.UNAUTHORIZED);
			}

			return chain.filter(exchange);
		};
	}

	private boolean isJwtValid(String jwt) {
		try {
			String subject = Jwts.parser().setSigningKey(env.getProperty("token.secret"))
					.parseClaimsJws(jwt)
					.getBody()
					.getSubject();

			if (subject == null || subject.isEmpty()) {
				return false;
			}
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}

	private Mono<Void> onError(ServerWebExchange exchange, String errorMessage, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		log.error(errorMessage);
		return response.setComplete();
	}

	public static class Config {

	}
}