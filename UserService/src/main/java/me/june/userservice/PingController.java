package me.june.userservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

	@GetMapping("/health_check")
	public String healthCheck() {
		return "It's Working in User Service";
	}
}
