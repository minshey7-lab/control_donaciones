package com.donaciones.control_donaciones.controller;

import com.donaciones.control_donaciones.entity.Donacion;
import com.donaciones.control_donaciones.service.DonacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/donaciones")
public class DonacionController {

    @Autowired
    private DonacionService donacionService;

    @GetMapping
    public List<Donacion> obtenerTodas() {
        return donacionService.listarDonaciones();
    }

    @PostMapping
    public Donacion crearDonacion(@RequestBody Donacion donacion) {
        return donacionService.guardarDonacion(donacion);
    }

    @GetMapping("/{id}")
    public Optional<Donacion> obtenerPorId(@PathVariable Long id) {
        return donacionService.obtenerPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarDonacion(@PathVariable Long id) {
        donacionService.eliminarDonacion(id);
    }
}