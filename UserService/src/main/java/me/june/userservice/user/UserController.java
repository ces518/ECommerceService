package me.june.userservice.user;

import lombok.RequiredArgsConstructor;
import me.june.userservice.user.dto.ResponseUser;
import me.june.userservice.user.dto.UserDto;
import me.june.userservice.user.dto.UserRequest;
import me.june.userservice.user.entity.User;
import me.june.userservice.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user-service/users")
@RequiredArgsConstructor
public class UserController {
	private final Environment env;
	private final UserService userService;
	private final ModelMapper mapper;

	@GetMapping("/welcome")
	public String welcome() {
		return env.getProperty("greeting.message");
	}

	@PostMapping
	public ResponseEntity<UserDto> createUser(@RequestBody final UserRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
	}

	@GetMapping
	public ResponseEntity<List<ResponseUser>> getUsers() {
		Iterable<User> users = userService.getUsers();
		List<ResponseUser> result = new ArrayList<>();
		users.forEach(v -> {
			result.add(mapper.map(v, ResponseUser.class));
		});
		return ResponseEntity.ok(result);
	}

	@GetMapping("{userId}")
	public ResponseEntity<ResponseUser> getUser(@PathVariable String userId) {
		UserDto userDto = userService.getUserById(userId);
		return ResponseEntity.ok(mapper.map(userDto, ResponseUser.class));
	}
}
