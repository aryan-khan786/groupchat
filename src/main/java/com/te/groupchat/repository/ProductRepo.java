package com.te.groupchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.te.groupchat.model.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {
}
