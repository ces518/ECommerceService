package me.june.userservice.user.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDto {
	private String email;
	private String pwd;
	private String name;
	private String userId;
	private LocalDateTime createdAt;

	private String encryptedPwd;

	private List<ResponseOrder> orders;
}
