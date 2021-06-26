package me.june.userservice;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PingController {
	private final Environment env;

	@GetMapping("/health_check")
	public String healthCheck() {
		return String.format("It's Working in User Service %s" +
						"token.secret = %s, token.expire = $s",
				env.getProperty("local.server.port"),
				env.getProperty("token.secret"),
				env.getProperty("token.expiration_time")
		);
	}
}
