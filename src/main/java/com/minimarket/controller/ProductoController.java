package com.minimarket.controller;

import com.minimarket.model.Producto;
import com.minimarket.security.services.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Producto crearProducto(@RequestBody Producto producto) {
        return service.guardarProducto(producto);
    }
}
