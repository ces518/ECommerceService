package me.june.userservice.user;

import lombok.RequiredArgsConstructor;
import me.june.userservice.user.dto.UserDto;
import me.june.userservice.user.dto.UserRequest;
import me.june.userservice.user.service.UserService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final Environment env;
	private final UserService userService;

	@GetMapping("/welcome")
	public String welcome() {
		return env.getProperty("greeting.message");
	}

	@PostMapping
	public ResponseEntity<UserDto> createUser(@RequestBody final UserRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
	}
}
