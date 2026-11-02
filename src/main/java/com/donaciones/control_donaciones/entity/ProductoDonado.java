
package com.donaciones.control_donaciones.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class ProductoDonado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String categoria;
    private Integer stockActual;

    @Column(nullable = false)
    private Boolean activo = true;

    public ProductoDonado() {
    }

    public ProductoDonado(String nombre, String categoria, Integer stockActual) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.stockActual = stockActual;
        this.activo = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
