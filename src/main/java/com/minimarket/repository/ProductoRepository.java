package com.minimarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minimarket.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
