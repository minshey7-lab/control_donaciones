
package com.donaciones.control_donaciones.controller;

import com.donaciones.control_donaciones.dto.DonanteDTO;
import com.donaciones.control_donaciones.entity.Donante;
import com.donaciones.control_donaciones.service.DonanteService;
import com.donaciones.control_donaciones.exception.RecursoNoEncontradoException;
import com.donaciones.control_donaciones.exception.RecursoNoEncontradoException;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donantes")
public class DonanteController {

    @Autowired
    private DonanteService donanteService;



    private DonanteDTO convertirDTO(Donante donante) {
        return new DonanteDTO(
                donante.getId(),
                donante.getNombre(),
                donante.getTelefono(),
                donante.getTipo(),
                donante.getDireccion()
        );
    }



    private Donante convertirEntidad(DonanteDTO datos) {
        Donante donante = new Donante();

        donante.setNombre(datos.getNombre());
        donante.setTelefono(datos.getTelefono());
        donante.setTipo(datos.getTipo());
        donante.setDireccion(datos.getDireccion());

        return donante;
    }


    @GetMapping
    public List<DonanteDTO> obtenerTodos() {
        return donanteService.listarDonantes()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }



    @GetMapping("/{id}")
    public DonanteDTO obtenerPorId(@PathVariable Long id) {

        Donante donante = donanteService.obtenerPorId(id)

                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "Donante no encontrado"));


        return convertirDTO(donante);
    }



    @PostMapping
    public DonanteDTO crearDonante(
            @Valid @RequestBody DonanteDTO datos) {

        Donante guardado = donanteService.guardarDonante(
                convertirEntidad(datos));

        return convertirDTO(guardado);
    }



    @PutMapping("/{id}")
    public DonanteDTO actualizarDonante(
            @PathVariable Long id,
            @Valid @RequestBody DonanteDTO datos) {

        Donante actualizado = donanteService.actualizarDonante(
                id, convertirEntidad(datos));

        return convertirDTO(actualizado);
    }



    @DeleteMapping("/{id}")
    public void eliminarDonante(@PathVariable Long id) {
        donanteService.eliminarDonante(id);
    }
}
