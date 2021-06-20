package me.june.firstservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/first-service")
public class FirstServiceController {

	@GetMapping("welcome")
	public String welcome(@RequestHeader("first-request") String header) {
		return "Welcome to the First Service";
	}

	@GetMapping("check")
	public String check(HttpServletRequest request) {
		log.info("IP = {}", request.getRemoteAddr());
		return "Hi, there. This is a message from First Service";
	}
}
