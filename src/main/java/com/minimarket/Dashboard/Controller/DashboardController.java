package com.minimarket.Dashboard.Controller;

import com.minimarket.Dashboard.dto.DashboardResumenResponse;
import com.minimarket.Dashboard.Service.DashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/resumen")
    @PreAuthorize("hasAuthority('ADMIN')")
    public DashboardResumenResponse obtenerResumen() {
        return dashboardService.obtenerResumen();
    }
}
