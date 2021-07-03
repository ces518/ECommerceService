package me.june.orderservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.june.orderservice.dto.Field;
import me.june.orderservice.dto.KafkaOrderDto;
import me.june.orderservice.dto.Payload;
import me.june.orderservice.dto.Schema;
import me.june.orderservice.order.dto.OrderDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProducer {
	private final KafkaTemplate<String, String> kafkaTemplate;

	List<Field> fields = List.of(
		new Field("string", true, "order_id"),
		new Field("string", true, "user_id"),
		new Field("string", true, "product_id"),
		new Field("int32", true, "qty"),
		new Field("int32", true, "unit_price"),
		new Field("int32", true, "total_price")
	);

	Schema schema = Schema.builder()
		.type("struct")
		.fields(fields)
		.optional(false)
		.name("orders")
		.build();

	public OrderDto send(String topic, OrderDto orderDto) {
		Payload payload = Payload.builder()
			.orderId(orderDto.getOrderId())
			.productId(orderDto.getProductId())
			.userId(orderDto.getUserId())
			.qty(orderDto.getQty())
			.unitPrice(orderDto.getUnitPrice())
			.totalPrice(orderDto.getTotalPrice())
			.build();

		KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema, payload);

		ObjectMapper mapper = new ObjectMapper();
		try {
			String body = mapper.writeValueAsString(kafkaOrderDto);
			kafkaTemplate.send(topic, body);
			log.info("Order Producer send data from the Order Service : ", orderDto);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return orderDto;
	}
}
