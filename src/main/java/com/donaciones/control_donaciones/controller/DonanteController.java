package com.donaciones.control_donaciones.controller;

import com.donaciones.control_donaciones.entity.Donante;
import com.donaciones.control_donaciones.service.DonanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/donantes")
public class DonanteController {

    @Autowired
    private DonanteService donanteService;

    @GetMapping
    public List<Donante> obtenerTodos() {
        return donanteService.listarDonantes();
    }

    @PostMapping
    public Donante crearDonante(@RequestBody Donante donante) {
        return donanteService.guardarDonante(donante);
    }

    @GetMapping("/{id}")
    public Optional<Donante> obtenerPorId(@PathVariable Long id) {
        return donanteService.obtenerPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarDonante(@PathVariable Long id) {
        donanteService.eliminarDonante(id);
    }
}