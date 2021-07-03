package me.june.catalogservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.june.catalogservice.catalog.entity.Catalog;
import me.june.catalogservice.catalog.repository.CatalogRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {
	private final CatalogRepository catalogRepository;

	@KafkaListener(topics = "example-catalog-topic")
	public void updateQty(String kafkaMessage) {
		log.info("Kafka Message : -> ", kafkaMessage);

		Map<Object, Object> map = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>(){});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		String productId = (String) map.get("productId");

		Catalog catalog = catalogRepository.findByProductId(productId).orElseThrow();
		int qty = (int) map.get("qty");
		catalog.setStock(catalog.getStock() - qty);

		catalogRepository.save(catalog);
	}
}
