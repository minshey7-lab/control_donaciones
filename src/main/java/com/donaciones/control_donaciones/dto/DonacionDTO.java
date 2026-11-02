
package com.donaciones.control_donaciones.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class DonacionDTO {

    private Long id;

    @NotBlank(message = "La descripción del alimento es obligatoria")
    @Size(max = 150, message = "La descripción no puede superar 150 caracteres")
    private String descripcionAlimento;

    @NotNull(message = "La cantidad es obligatoria")
    @DecimalMin(value = "0.01", message = "La cantidad debe ser mayor que cero")
    private Double cantidad;

    @NotBlank(message = "La unidad de medida es obligatoria")
    @Size(max = 20, message = "La unidad no puede superar 20 caracteres")
    private String unidadMedida;

    @NotNull(message = "La fecha de donación es obligatoria")
    private LocalDate fechaDonacion;

    @NotNull(message = "La fecha de caducidad es obligatoria")
    private LocalDate fechaCaducidad;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 30, message = "El estado no puede superar 30 caracteres")
    private String estado;

    @NotNull(message = "Debe seleccionar un donante")
    private Long donanteId;

    public DonacionDTO() {
    }

    public DonacionDTO(Long id, String descripcionAlimento,
                       Double cantidad, String unidadMedida,
                       LocalDate fechaDonacion, LocalDate fechaCaducidad,
                       String estado, Long donanteId) {

        this.id = id;
        this.descripcionAlimento = descripcionAlimento;
        this.cantidad = cantidad;
        this.unidadMedida = unidadMedida;
        this.fechaDonacion = fechaDonacion;
        this.fechaCaducidad = fechaCaducidad;
        this.estado = estado;
        this.donanteId = donanteId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcionAlimento() {
        return descripcionAlimento;
    }

    public void setDescripcionAlimento(String descripcionAlimento) {
        this.descripcionAlimento = descripcionAlimento;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public LocalDate getFechaDonacion() {
        return fechaDonacion;
    }

    public void setFechaDonacion(LocalDate fechaDonacion) {
        this.fechaDonacion = fechaDonacion;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getDonanteId() {
        return donanteId;
    }

    public void setDonanteId(Long donanteId) {
        this.donanteId = donanteId;
    }
}
