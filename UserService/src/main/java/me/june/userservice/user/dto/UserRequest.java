package me.june.userservice.user.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class UserRequest {
	@NotNull(message = "Email cannot be null")
	@Size(min = 2, message = "Email not be less than two characters")
	@Email
	private String email;

	@NotNull(message = "Password cannot be null")
	@Size(min = 8, message = "Password must be equal or greater than 8 characters and less then 16 characters")
	private String pwd;

	@NotNull(message = "Name cannot be null")
	@Size(min = 2, message = "Name not be less than two characters")
	private String name;
}
