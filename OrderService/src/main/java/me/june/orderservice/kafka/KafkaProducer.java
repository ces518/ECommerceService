package me.june.orderservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.june.orderservice.order.dto.OrderDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {
	private final KafkaTemplate<String, String> kafkaTemplate;

	public OrderDto send(String topic, OrderDto orderDto) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String body = objectMapper.writeValueAsString(orderDto);
			kafkaTemplate.send(topic, body);
			log.info("Kafka Producer send data from the Order Service : ", orderDto);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return orderDto;
	}
}
