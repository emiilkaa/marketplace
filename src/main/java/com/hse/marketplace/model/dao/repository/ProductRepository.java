package com.hse.marketplace.model.dao.repository;

import com.hse.marketplace.model.dao.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
