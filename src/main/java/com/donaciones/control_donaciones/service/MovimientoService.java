
package com.donaciones.control_donaciones.service;

import com.donaciones.control_donaciones.dto.ResumenIndicadoresDTO;
import com.donaciones.control_donaciones.entity.MovimientoAlimento;
import com.donaciones.control_donaciones.entity.ProductoDonado;
import com.donaciones.control_donaciones.repository.MovimientoRepository;
import com.donaciones.control_donaciones.repository.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // Consultar todos los movimientos
    public List<MovimientoAlimento> listarMovimientos() {
        return movimientoRepository.findAll();
    }

    // Filtrar movimientos por tipo
    public List<MovimientoAlimento> listarPorTipo(String tipo) {

        if (tipo == null ||
                (!tipo.equalsIgnoreCase("ENTRADA") &&
                        !tipo.equalsIgnoreCase("SALIDA"))) {

            throw new IllegalArgumentException(
                    "El tipo de movimiento debe ser ENTRADA o SALIDA"
            );
        }

        return movimientoRepository.findAll()
                .stream()
                .filter(movimiento ->
                        tipo.equalsIgnoreCase(
                                movimiento.getTipoMovimiento()))
                .toList();
    }

    // Calcular unidades recibidas y entregadas
    public ResumenIndicadoresDTO obtenerIndicadores() {

        long recibidas = 0;
        long entregadas = 0;

        List<MovimientoAlimento> movimientos =
                movimientoRepository.findAll();

        for (MovimientoAlimento movimiento : movimientos) {

            if (movimiento.getCantidad() == null) {
                continue;
            }

            if ("ENTRADA".equalsIgnoreCase(
                    movimiento.getTipoMovimiento())) {

                recibidas += movimiento.getCantidad();

            } else if ("SALIDA".equalsIgnoreCase(
                    movimiento.getTipoMovimiento())) {

                entregadas += movimiento.getCantidad();
            }
        }

        return new ResumenIndicadoresDTO(recibidas, entregadas);
    }

    // Registrar entrada o salida
    @Transactional
    public MovimientoAlimento registrarMovimiento(
            MovimientoAlimento movimiento) {

        if (movimiento == null) {
            throw new IllegalArgumentException(
                    "El movimiento es obligatorio");
        }

        if (movimiento.getCantidad() == null ||
                movimiento.getCantidad() <= 0) {

            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor que cero");
        }

        String tipo = movimiento.getTipoMovimiento();

        if (tipo == null ||
                (!tipo.equalsIgnoreCase("ENTRADA") &&
                        !tipo.equalsIgnoreCase("SALIDA"))) {

            throw new IllegalArgumentException(
                    "El tipo de movimiento debe ser ENTRADA o SALIDA");
        }

        if (movimiento.getProducto() == null ||
                movimiento.getProducto().getId() == null) {

            throw new IllegalArgumentException(
                    "Debe indicar un producto válido");
        }

        ProductoDonado producto = productoRepository
                .findById(movimiento.getProducto().getId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Producto no encontrado"));

        // Impedir movimientos de productos desactivados
        if (!Boolean.TRUE.equals(producto.getActivo())) {
            throw new IllegalArgumentException(
                    "No se pueden registrar movimientos de un producto desactivado");
        }

        if (producto.getStockActual() == null ||
                producto.getStockActual() < 0) {

            throw new IllegalArgumentException(
                    "El producto tiene existencias inválidas");
        }

        // Entrada: aumentar existencias
        if (tipo.equalsIgnoreCase("ENTRADA")) {

            producto.setStockActual(
                    producto.getStockActual() +
                            movimiento.getCantidad()
            );

        } else {

            // Salida: comprobar existencias suficientes
            if (movimiento.getCantidad() >
                    producto.getStockActual()) {

                throw new IllegalArgumentException(
                        "No hay suficientes existencias. Stock disponible: "
                                + producto.getStockActual());
            }

            producto.setStockActual(
                    producto.getStockActual() -
                            movimiento.getCantidad()
            );
        }

        movimiento.setTipoMovimiento(tipo.toUpperCase());
        movimiento.setProducto(producto);

        productoRepository.save(producto);

        return movimientoRepository.save(movimiento);
    }
}
