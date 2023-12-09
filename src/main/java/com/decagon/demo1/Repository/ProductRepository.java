package com.decagon.demo1.Repository;

import com.decagon.demo1.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findProductById (Long id);

}
