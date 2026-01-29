package com.minimarket.security.services;

import com.minimarket.model.Producto;
import com.minimarket.repository.ProductoRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public List<Producto> listarProductos() {
        return repository.findAll();
    }

    public @NonNull Producto guardarProducto(@NonNull Producto producto) {
        return Objects.requireNonNull(repository.save(producto), "El producto guardado no puede ser nulo");
    }
}
