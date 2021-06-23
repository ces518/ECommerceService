package me.june.orderservice.order.repository;

import me.june.orderservice.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
	Optional<Order> findByOrderId(String orderId);
	Iterable<Order> findByUserId(String userId);
}
