package com.donaciones.control_donaciones.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "donaciones")
public class Donacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion_alimento", nullable = false, length = 150)
    private String descripcionAlimento;

    @Column(nullable = false)
    private Double cantidad;

    @Column(name = "unidad_medida", nullable = false, length = 20)
    private String unidadMedida;

    @Column(name = "fecha_donacion", nullable = false)
    private LocalDate fechaDonacion;

    @Column(name = "fecha_caducidad", nullable = false)
    private LocalDate fechaCaducidad;

    @Column(nullable = false, length = 30)
    private String estado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "donante_id", nullable = false)
    private Donante donante;

    public Donacion() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescripcionAlimento() { return descripcionAlimento; }
    public void setDescripcionAlimento(String descripcionAlimento) { this.descripcionAlimento = descripcionAlimento; }
    public Double getCantidad() { return cantidad; }
    public void setCantidad(Double cantidad) { this.cantidad = cantidad; }
    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }
    public LocalDate getFechaDonacion() { return fechaDonacion; }
    public void setFechaDonacion(LocalDate fechaDonacion) { this.fechaDonacion = fechaDonacion; }
    public LocalDate getFechaCaducidad() { return fechaCaducidad; }
    public void setFechaCaducidad(LocalDate fechaCaducidad) { this.fechaCaducidad = fechaCaducidad; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Donante getDonante() { return donante; }
    public void setDonante(Donante donante) { this.donante = donante; }
}