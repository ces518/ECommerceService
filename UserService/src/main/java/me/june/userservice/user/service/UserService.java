package me.june.userservice.user.service;

import lombok.RequiredArgsConstructor;
import me.june.userservice.user.dto.UserDto;
import me.june.userservice.user.dto.UserRequest;
import me.june.userservice.user.entity.User;
import me.june.userservice.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
	private final UserRepository repository;
	private final ModelMapper mapper;

	@Transactional
	public UserDto createUser(final UserRequest request) {
		User entity = mapper.map(request, User.class);
		entity.setUserId(UUID.randomUUID().toString());
		entity.setEncryptedPwd("encrypted password");
		User savedUser = repository.save(entity);
		return mapper.map(savedUser, UserDto.class);
	}
}
