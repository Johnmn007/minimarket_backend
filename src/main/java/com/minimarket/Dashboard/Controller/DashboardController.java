package com.minimarket.Dashboard.Controller;

import com.minimarket.Dashboard.dto.DashboardResumenResponse;
import com.minimarket.Dashboard.Service.DashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/resumen")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DashboardResumenResponse> obtenerResumen() {
        logger.info("📊 Usuario ADMIN solicitando resumen de dashboard");
        DashboardResumenResponse resumen = dashboardService.obtenerResumen();
        return ResponseEntity.ok(resumen);
    }
    
    @GetMapping("/estadisticas-productos")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, Long>> obtenerEstadisticasProductos() {
        logger.info("📦 Obteniendo estadísticas de productos");
        return ResponseEntity.ok(dashboardService.obtenerEstadisticasProductos());
    }
}