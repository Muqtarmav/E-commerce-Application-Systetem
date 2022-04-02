package com.phoenix.data.repository;

import com.phoenix.data.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product ,Long> {

    Optional<Product> findByName(String name);

    Optional<Product> findById(Long id);

}

