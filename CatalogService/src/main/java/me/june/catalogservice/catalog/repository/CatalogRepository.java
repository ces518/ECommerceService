package me.june.catalogservice.catalog.repository;

import me.june.catalogservice.catalog.entity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {
	Optional<Catalog> findByProductId(String productId);
}
