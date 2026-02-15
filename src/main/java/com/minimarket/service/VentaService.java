package com.minimarket.service;

import com.minimarket.dto.venta.FinalizarVentaRequest;
import com.minimarket.dto.venta.FinalizarVentaResponse;
import com.minimarket.entity.*;
import com.minimarket.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService {

    private final VentaRepository ventaRepo;
    private final DetalleVentaRepository detalleRepo;
    private final PagoRepository pagoRepo;
    private final MetodoPagoRepository metodoPagoRepo;
    private final ProductoRepository productoRepo;

    public VentaService(
            VentaRepository ventaRepo,
            DetalleVentaRepository detalleRepo,
            PagoRepository pagoRepo,
            MetodoPagoRepository metodoPagoRepo,
            ProductoRepository productoRepo) {
        this.ventaRepo = ventaRepo;
        this.detalleRepo = detalleRepo;
        this.pagoRepo = pagoRepo;
        this.metodoPagoRepo = metodoPagoRepo;
        this.productoRepo = productoRepo;
    }

    @Transactional
    public FinalizarVentaResponse finalizarVenta(
            FinalizarVentaRequest request,
            Usuario vendedor) {
        Long metodoPagoId = request.getMetodoPagoId();
        if (metodoPagoId == null) {
            throw new RuntimeException("ID de método de pago no puede ser nulo");
        }

        MetodoPago metodoPago = metodoPagoRepo.findById(metodoPagoId)
                .orElseThrow(() -> new RuntimeException("Método de pago no existe"));

        if (!metodoPago.getActivo()) {
            throw new RuntimeException("Método de pago no disponible");
        }

        if (!metodoPago.getNombre().equals("EFECTIVO")
                && (request.getReferencia() == null || request.getReferencia().isBlank())) {
            throw new RuntimeException("Referencia obligatoria para YAPE o PLIN");
        }

        Venta venta = new Venta();
        venta.setVendedor(vendedor);
        venta.setFecha(LocalDateTime.now());
        venta.setEstado("PENDIENTE_PAGO");
        venta.setTotal(BigDecimal.ZERO);

        venta = ventaRepo.save(venta);

        BigDecimal total = BigDecimal.ZERO;
        List<DetalleVenta> detalles = new ArrayList<>();

        for (var item : request.getItems()) {
            Long productoId = item.getProductoId();
            if (productoId == null) {
                throw new RuntimeException("ID de producto no puede ser nulo");
            }
            Producto producto = productoRepo.findById(productoId)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            BigDecimal precio = producto.getPrecio(); // precio desde BD
            BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(item.getCantidad()));
            total = total.add(subtotal);

            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(precio);

            detalles.add(detalle);
        }

        detalleRepo.saveAll(detalles);

        venta.setTotal(total);
        venta.setEstado("PAGADO");
        ventaRepo.save(venta);

        Pago pago = new Pago();
        pago.setVenta(venta);
        pago.setMetodoPago(metodoPago);
        pago.setMonto(total);
        pago.setEstado("CONFIRMADO");
        pago.setReferencia(request.getReferencia());
        pago.setFecha(LocalDateTime.now());

        pagoRepo.save(pago);

        FinalizarVentaResponse response = new FinalizarVentaResponse();
        response.setVentaId(venta.getId());
        response.setEstado(venta.getEstado());
        response.setTotal(total);
        response.setMetodoPago(metodoPago.getNombre());

        return response;
    }
}
