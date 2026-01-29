package com.minimarket.controller;

import com.minimarket.model.Producto;
import com.minimarket.security.services.ProductoService;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    // GET → listar productos
    @GetMapping
    public List<Producto> listarProductos() {
        return service.listarProductos();
    }

    // POST → crear producto ✅
    @PostMapping
    public @NonNull Producto crearProducto(@RequestBody Producto producto) {
        return Objects.requireNonNull(service.guardarProducto(producto),
                "El producto creado no puede ser nulo");
    }
}
