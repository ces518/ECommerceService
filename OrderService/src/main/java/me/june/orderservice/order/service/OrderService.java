package me.june.orderservice.order.service;

import lombok.RequiredArgsConstructor;
import me.june.orderservice.order.entity.Order;
import me.june.orderservice.order.dto.OrderDto;
import me.june.orderservice.order.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository repository;

	@Transactional
	public OrderDto createOrder(OrderDto orderDetail) {
		orderDetail.setOrderId(UUID.randomUUID().toString());
		orderDetail.setTotalPrice(orderDetail.getQty() * orderDetail.getUnitPrice());

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		Order order = modelMapper.map(orderDetail, Order.class);
		repository.save(order);

		OrderDto returnValue = modelMapper.map(order, OrderDto.class);
		return returnValue;
	}

	public OrderDto getOrderByOrderId(String orderId) {
		Order order = repository.findByOrderId(orderId)
				.orElseThrow(RuntimeException::new);
		OrderDto orderDto = new ModelMapper().map(order, OrderDto.class);
		return orderDto;
	}

	public Iterable<Order> getOrdersByUserId(String userId) {
		return repository.findByUserId(userId);
	}
}
