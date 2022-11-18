package com.vont.myshopping.repository;

import com.vont.myshopping.models.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory_IdAndAvailable(Long id, Boolean available);
    Page<Product> findAllByAvailable(Boolean available, Pageable pageable);
}