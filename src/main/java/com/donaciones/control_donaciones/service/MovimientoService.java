package com.donaciones.control_donaciones.service;

import com.donaciones.control_donaciones.entity.MovimientoAlimento;
import com.donaciones.control_donaciones.entity.ProductoDonado;
import com.donaciones.control_donaciones.repository.MovimientoRepository;
import com.donaciones.control_donaciones.repository.ProductoRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<MovimientoAlimento> listarMovimientos() {
        return movimientoRepository.findAll();
    }

    public MovimientoAlimento registrarMovimiento(@NonNull MovimientoAlimento movimiento) {
        ProductoDonado producto = productoRepository.findById(movimiento.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if ("ENTRADA".equalsIgnoreCase(movimiento.getTipoMovimiento())) {
            // Regla: Aumentar stock en recepción
            producto.setStockActual(producto.getStockActual() + movimiento.getCantidad());
        } else if ("SALIDA".equalsIgnoreCase(movimiento.getTipoMovimiento())) {
            // Regla Obligatoria del PF-30: Validar stock suficiente
            if (movimiento.getCantidad() > producto.getStockActual()) {
                throw new IllegalArgumentException("Error: La cantidad a entregar supera las existencias disponibles (" + producto.getStockActual() + ")");
            }
            producto.setStockActual(producto.getStockActual() - movimiento.getCantidad());
        }

        productoRepository.save(producto);
        return movimientoRepository.save(movimiento);
    }
}