package me.june.userservice.user.client;

import me.june.userservice.user.dto.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("order-service")
public interface OrderServiceClient {

	@GetMapping("/order-service/{userId/orders_ng")
	List<ResponseOrder> getOrders(@PathVariable String userId);
}
