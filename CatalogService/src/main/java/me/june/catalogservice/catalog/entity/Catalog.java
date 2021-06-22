package me.june.catalogservice.catalog.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "catalogs")
@EqualsAndHashCode(of = "id")
public class Catalog implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String productId;

	@Column(nullable = false)
	private String productName;

	@Column(nullable = false)
	private Integer stock;

	@Column(nullable = false)
	private Integer unitPrice;

	@Column(nullable = false, updatable = false, insertable = false)
	@ColumnDefault("CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;
}
