package com.donaciones.control_donaciones.controller;

import com.donaciones.control_donaciones.entity.MovimientoAlimento;
import com.donaciones.control_donaciones.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    public List<MovimientoAlimento> listar() {
        return movimientoService.listarMovimientos();
    }

    @PostMapping
    public MovimientoAlimento registrar(@RequestBody MovimientoAlimento movimiento) {
        return movimientoService.registrarMovimiento(movimiento);
    }
}