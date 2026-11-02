
package com.donaciones.control_donaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class DonanteDTO {

    private Long id;

    @NotBlank(message = "El nombre del donante es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    private String nombre;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 20, message = "El teléfono no puede superar 20 caracteres")
    @Pattern(
            regexp = "[0-9+() -]+",
            message = "El teléfono contiene caracteres no válidos"
    )
    private String telefono;

    @NotBlank(message = "El tipo de donante es obligatorio")
    @Pattern(
            regexp = "(?i)PERSONA|ORGANIZACION",
            message = "El tipo debe ser PERSONA u ORGANIZACION"
    )
    private String tipo;

    @NotBlank(message = "La dirección del donante es obligatoria")
    @Size(max = 150, message = "La dirección no puede superar 150 caracteres")
    private String direccion;

    public DonanteDTO() {
    }





    public DonanteDTO(Long id, String nombre, String telefono,
                      String tipo, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.tipo = tipo;
        this.direccion = direccion;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
