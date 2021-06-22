package me.june.catalogservice.catalog.service;

import lombok.RequiredArgsConstructor;
import me.june.catalogservice.catalog.entity.Catalog;
import me.june.catalogservice.catalog.repository.CatalogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CatalogService {
	private final CatalogRepository repository;

	public Iterable<Catalog> getCatalogs() {
		return repository.findAll();
	}
}
