package com.example.n01710904_poorvapatel_test4ims.repository;

import com.example.n01710904_poorvapatel_test4ims.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}