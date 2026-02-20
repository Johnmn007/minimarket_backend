package com.minimarket.repository;

import com.minimarket.entity.Venta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    @Query("""
            SELECT COALESCE(SUM(v.total),0)
            FROM Venta v
            WHERE FUNCTION('DATE', v.fecha) = CURRENT_DATE
            AND v.estado = 'PAGADO'
            """)
    BigDecimal totalVentasHoy();

    @Query("""
            SELECT COUNT(v)
            FROM Venta v
            WHERE FUNCTION('DATE', v.fecha) = CURRENT_DATE
            AND v.estado = 'PAGADO'
            """)
    Long cantidadVentasHoy();

    @Query("SELECT v FROM Venta v WHERE v.fecha BETWEEN :inicio AND :fin AND v.estado = 'PAGADO'")
    List<Venta> findVentasDelDia(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin);

    @Query("SELECT COALESCE(SUM(v.total),0) FROM Venta v WHERE v.fecha BETWEEN :inicio AND :fin AND v.estado = 'PAGADO'")
    BigDecimal totalVentasDelDia(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin);

}
