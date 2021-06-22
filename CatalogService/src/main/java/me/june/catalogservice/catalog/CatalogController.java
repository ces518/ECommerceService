package me.june.catalogservice.catalog;

import lombok.RequiredArgsConstructor;
import me.june.catalogservice.catalog.dto.ResponseCatalog;
import me.june.catalogservice.catalog.entity.Catalog;
import me.june.catalogservice.catalog.service.CatalogService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogController {
	private final CatalogService service;

	@GetMapping("/catalogs")
	public ResponseEntity<List<ResponseCatalog>> getCatalogs() {
		Iterable<Catalog> catalogs = service.getCatalogs();

		List<ResponseCatalog> result = new ArrayList<>();
		catalogs.forEach(v -> {
			result.add(new ModelMapper().map(v, ResponseCatalog.class));
		});
		return ResponseEntity.ok(result);
	}
}
