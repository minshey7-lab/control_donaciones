
package com.donaciones.control_donaciones.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public class MovimientoDTO {

    private Long id;

    @NotBlank(message = "El tipo de movimiento es obligatorio")
    @Pattern(
            regexp = "(?i)ENTRADA|SALIDA",
            message = "El tipo de movimiento debe ser ENTRADA o SALIDA"
    )
    private String tipoMovimiento;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor que cero")
    private Integer cantidad;

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

    private String nombreProducto;
    private LocalDateTime fecha;

    public MovimientoDTO() {
    }

    public MovimientoDTO(Long id, String tipoMovimiento,
                         Integer cantidad, Long productoId,
                         String nombreProducto, LocalDateTime fecha) {

        this.id = id;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
