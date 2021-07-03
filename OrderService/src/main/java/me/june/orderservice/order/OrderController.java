package me.june.orderservice.order;

import lombok.RequiredArgsConstructor;
import me.june.orderservice.kafka.KafkaProducer;
import me.june.orderservice.kafka.OrderProducer;
import me.june.orderservice.order.dto.OrderDto;
import me.june.orderservice.order.dto.RequestOrder;
import me.june.orderservice.order.dto.ResponseOrder;
import me.june.orderservice.order.entity.Order;
import me.june.orderservice.order.service.OrderService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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
import java.util.UUID;

@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {
	private final OrderService service;
	private final KafkaProducer kafkaProducer;
	private final OrderProducer orderProducer;

	@PostMapping("/{userId}/orders")
	public ResponseEntity<ResponseOrder> createOrder(
			@PathVariable String userId,
			@RequestBody RequestOrder orderDetail) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		OrderDto orderDto = mapper.map(orderDetail, OrderDto.class);
		orderDto.setUserId(userId);
		orderDto.setOrderId(UUID.randomUUID().toString());
		orderDto.setTotalPrice(orderDto.getQty() * orderDetail.getUnitPrice());

		/* JPA
			service.createOrder(orderDto);
		*/

		/* Send To Kafka */
		kafkaProducer.send("example-catalog-topic", orderDto);
		orderProducer.send("orders", orderDto);
		/**/

		ResponseOrder responseOrder = mapper.map(orderDto, ResponseOrder.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
	}

	@GetMapping("/{userId}/orders")
	public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable String userId) {
		Iterable<Order> orders = service.getOrdersByUserId(userId);

		List<ResponseOrder> result = new ArrayList<>();
		orders.forEach(v -> {
			result.add(new ModelMapper().map(v, ResponseOrder.class));
		});
		return ResponseEntity.ok(result);
	}
}
