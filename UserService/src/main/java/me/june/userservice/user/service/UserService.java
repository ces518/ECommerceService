package me.june.userservice.user.service;

import lombok.RequiredArgsConstructor;
import me.june.userservice.user.dto.UserDto;
import me.june.userservice.user.dto.UserRequest;
import me.june.userservice.user.entity.User;
import me.june.userservice.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper mapper;

	@Transactional
	public UserDto createUser(final UserRequest request) {
		User entity = mapper.map(request, User.class);
		entity.setUserId(UUID.randomUUID().toString());
		entity.setEncryptedPwd(passwordEncoder.encode(request.getPwd()));
		User savedUser = repository.save(entity);
		return mapper.map(savedUser, UserDto.class);
	}

	public UserDto getUserById(String userId) {
		User user = repository.findByUserId(userId)
				.orElseThrow(RuntimeException::new);
		return mapper.map(user, UserDto.class);
	}

	public Iterable<User> getUsers() {
		return repository.findAll();
	}
}
