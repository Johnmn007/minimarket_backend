package com.minimarket.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Usuario vendedor;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalles;

    @OneToOne(mappedBy = "venta", cascade = CascadeType.ALL)
    private Pago pago;

    // getters y setters
    public Long getId() {
        return id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public Pago getPago() {
        return pago;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }
}
