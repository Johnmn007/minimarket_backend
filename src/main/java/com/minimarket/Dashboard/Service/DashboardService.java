package com.minimarket.Dashboard.Service;

import com.minimarket.Dashboard.dto.DashboardResumenResponse;
import com.minimarket.Dashboard.dto.ProductoStockBajoResponse;
import com.minimarket.entity.Producto;
import com.minimarket.repository.ProductoRepository;
import com.minimarket.repository.VentaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final VentaRepository ventaRepo;
    private final ProductoRepository productoRepo;

    public DashboardService(VentaRepository ventaRepo,
            ProductoRepository productoRepo) {
        this.ventaRepo = ventaRepo;
        this.productoRepo = productoRepo;
    }

    public DashboardResumenResponse obtenerResumen() {

        BigDecimal totalHoy = ventaRepo.totalVentasHoy();
        Long cantidadHoy = ventaRepo.cantidadVentasHoy();

        List<ProductoStockBajoResponse> stockBajo = productoRepo.findAll()
                .stream()
                .filter(p -> p.getStock() <= p.getStockMinimo())
                .map(p -> new ProductoStockBajoResponse(
                        p.getId(),
                        p.getNombre(),
                        p.getStock()))
                .collect(Collectors.toList());

        DashboardResumenResponse response = new DashboardResumenResponse();
        response.setVentasHoy(totalHoy);
        response.setCantidadVentasHoy(cantidadHoy);
        response.setProductosStockBajo(stockBajo);

        return response;
    }
}
