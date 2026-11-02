
package com.donaciones.control_donaciones.controller;

import com.donaciones.control_donaciones.dto.DonacionDTO;
import com.donaciones.control_donaciones.entity.Donacion;
import com.donaciones.control_donaciones.entity.Donante;
import com.donaciones.control_donaciones.service.DonacionService;
import com.donaciones.control_donaciones.exception.RecursoNoEncontradoException;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donaciones")
public class DonacionController {

    @Autowired
    private DonacionService donacionService;




    private DonacionDTO convertirDTO(Donacion donacion) {

        return new DonacionDTO(
                donacion.getId(),
                donacion.getDescripcionAlimento(),
                donacion.getCantidad(),
                donacion.getUnidadMedida(),
                donacion.getFechaDonacion(),
                donacion.getFechaCaducidad(),
                donacion.getEstado(),
                donacion.getDonante().getId()
        );
    }




    private Donacion convertirEntidad(DonacionDTO datos) {

        Donacion donacion = new Donacion();

        donacion.setDescripcionAlimento(
                datos.getDescripcionAlimento()
        );

        donacion.setCantidad(datos.getCantidad());

        donacion.setUnidadMedida(
                datos.getUnidadMedida()
        );

        donacion.setFechaDonacion(
                datos.getFechaDonacion()
        );

        donacion.setFechaCaducidad(
                datos.getFechaCaducidad()
        );

        donacion.setEstado(datos.getEstado());

        if (datos.getDonanteId() != null) {
            Donante donante = new Donante();
            donante.setId(datos.getDonanteId());
            donacion.setDonante(donante);
        }

        return donacion;
    }



    @GetMapping
    public List<DonacionDTO> obtenerTodas() {

        return donacionService.listarDonaciones()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }



    @GetMapping("/{id}")
    public DonacionDTO obtenerPorId(@PathVariable Long id) {

        Donacion donacion = donacionService.obtenerPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Donación no encontrada"
                ));

        return convertirDTO(donacion);
    }



    @PostMapping
    public DonacionDTO crearDonacion(
            @Valid @RequestBody DonacionDTO datos) {

        Donacion guardada = donacionService.guardarDonacion(
                convertirEntidad(datos)
        );

        return convertirDTO(guardada);
    }



    @PutMapping("/{id}")
    public DonacionDTO actualizarDonacion(
            @PathVariable Long id,
            @Valid @RequestBody DonacionDTO datos) {

        Donacion actualizada = donacionService.actualizarDonacion(
                id, convertirEntidad(datos)
        );

        return convertirDTO(actualizada);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDonacion(
            @PathVariable Long id) {

        donacionService.eliminarDonacion(id);

        return ResponseEntity.noContent().build();
    }
}
