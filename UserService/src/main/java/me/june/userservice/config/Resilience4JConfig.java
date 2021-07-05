package me.june.userservice.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Duration;

@Configuration
public class Resilience4JConfig {

	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration() {
		CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
			.failureRateThreshold(4) // Circuit Breaker 를 Open 할지 결정하는 failure Rate (default: 50)
			.waitDurationInOpenState(Duration.ofMillis(1000)) // Circuit Breaker Open 상태를 유지하는 시간, 이 기간 이후 half-open 상태 (default: 60)
			// half-open 상태일 경우 해당 서비스가 정상화 되었는지 확인을 시도함
			.slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED) // Circuit 을 닫을때 결과 기록시 Count 기반 / Time 기반 (default: 60)
			.slidingWindowSize(2) // Circuit 이 닫힐때 호출 결과를 기록시 사용되는 Sliding Window 의 크기 (default: 100)
			.build();

		TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
			.timeoutDuration(Duration.ofSeconds(4)) // 해당 서비스가 4초내에 응답이 오지 않을경우 Circuit open 짖어하는 time limit
			.build();

		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
			.circuitBreakerConfig(circuitBreakerConfig)
			.timeLimiterConfig(timeLimiterConfig)
			.build()
		);
	}
}
