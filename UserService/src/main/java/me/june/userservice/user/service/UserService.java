package me.june.userservice.user.service;

import lombok.RequiredArgsConstructor;
import me.june.userservice.user.client.OrderServiceClient;
import me.june.userservice.user.dto.ResponseOrder;
import me.june.userservice.user.dto.UserDto;
import me.june.userservice.user.dto.UserRequest;
import me.june.userservice.user.entity.User;
import me.june.userservice.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper mapper;

	private final RestTemplate restTemplate;
	private final Environment env;

	private final OrderServiceClient orderServiceClient;

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
		UserDto userDto = mapper.map(user, UserDto.class);

		/*
		RestTemplate
		String orderUrl = String.format(env.getProperty("order_service.url"), userId);
		ResponseEntity<List<ResponseOrder>> ordersResponse = restTemplate.exchange(
			orderUrl,
			HttpMethod.GET,
			null,
			new ParameterizedTypeReference<List<ResponseOrder>>() {} // Super Type Token
		);
		List<ResponseOrder> responseOrders = ordersResponse.getBody();
		*/

		// FeignClient
		List<ResponseOrder> responseOrders = orderServiceClient.getOrders(userId);

		userDto.setOrders(responseOrders);
		return userDto;
	}

	public Iterable<User> getUsers() {
		return repository.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		User user = repository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(),
				user.getEncryptedPwd(),
				new ArrayList<>()
		);
	}

	public UserDto getUserDetailsByEmail(String email) {
		User user = repository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(email));
		return mapper.map(user, UserDto.class);
	}
}
