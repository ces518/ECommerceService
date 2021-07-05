package me.june.userservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

//@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("LocalDate.now().toEpochDay() = " + LocalDate.now().toEpochDay());
	}

}

