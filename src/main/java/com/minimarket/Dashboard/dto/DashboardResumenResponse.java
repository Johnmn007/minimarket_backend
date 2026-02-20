package com.minimarket.Dashboard.dto;

import java.math.BigDecimal;
import java.util.List;

public class DashboardResumenResponse {

    private BigDecimal ventasHoy;
    private Long cantidadVentasHoy;
    private List<ProductoStockBajoResponse> productosStockBajo;

    public DashboardResumenResponse() {
    }

    public BigDecimal getVentasHoy() {
        return ventasHoy;
    }

    public void setVentasHoy(BigDecimal ventasHoy) {
        this.ventasHoy = ventasHoy;
    }

    public Long getCantidadVentasHoy() {
        return cantidadVentasHoy;
    }

    public void setCantidadVentasHoy(Long cantidadVentasHoy) {
        this.cantidadVentasHoy = cantidadVentasHoy;
    }

    public List<ProductoStockBajoResponse> getProductosStockBajo() {
        return productosStockBajo;
    }

    public void setProductosStockBajo(List<ProductoStockBajoResponse> productosStockBajo) {
        this.productosStockBajo = productosStockBajo;
    }
}
