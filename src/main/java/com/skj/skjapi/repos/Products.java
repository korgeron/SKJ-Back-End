package com.skj.skjapi.repos;

import com.skj.skjapi.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Products extends JpaRepository<Product , Long> {
    List<Product> findAllByCategory(String category);
}
