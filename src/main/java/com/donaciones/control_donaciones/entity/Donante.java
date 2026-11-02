package com.donaciones.control_donaciones.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "donantes")
public class Donante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(nullable = false, length = 50)
    private String tipo;

    @Column(nullable = false, length = 150)
    private String direccion;

    @OneToMany(mappedBy = "donante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Donacion> donaciones;

    public Donante() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }


    public List<Donacion> getDonaciones() {
        return donaciones;
    }
}