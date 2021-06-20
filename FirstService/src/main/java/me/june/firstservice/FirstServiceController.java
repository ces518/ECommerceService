package me.june.firstservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/first-service")
public class FirstServiceController {

	@GetMapping("welcome")
	public String welcome(@RequestHeader("first-request") String header) {
		return "Welcome to the First Service";
	}
}
