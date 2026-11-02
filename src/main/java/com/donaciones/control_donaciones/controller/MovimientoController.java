
package com.donaciones.control_donaciones.controller;

import com.donaciones.control_donaciones.dto.MovimientoDTO;
import com.donaciones.control_donaciones.dto.ResumenIndicadoresDTO;
import com.donaciones.control_donaciones.entity.MovimientoAlimento;
import com.donaciones.control_donaciones.entity.ProductoDonado;
import com.donaciones.control_donaciones.service.MovimientoService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;



    private MovimientoDTO convertirDTO(
            MovimientoAlimento movimiento) {

        ProductoDonado producto = movimiento.getProducto();

        return new MovimientoDTO(
                movimiento.getId(),
                movimiento.getTipoMovimiento(),
                movimiento.getCantidad(),
                producto != null ? producto.getId() : null,
                producto != null ? producto.getNombre() : null,
                movimiento.getFecha()
        );
    }




    @GetMapping
    public List<MovimientoDTO> listar() {

        return movimientoService.listarMovimientos()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }



    @GetMapping("/indicadores")
    public ResumenIndicadoresDTO indicadores() {
        return movimientoService.obtenerIndicadores();
    }




    @GetMapping("/tipo/{tipo}")
    public List<MovimientoDTO> movimientosPorTipo(
            @PathVariable String tipo) {

        return movimientoService.listarPorTipo(tipo)
                .stream()
                .map(this::convertirDTO)
                .toList();
    }




    @PostMapping
    public MovimientoDTO registrar(
            @Valid @RequestBody MovimientoDTO datos) {

        MovimientoAlimento movimiento =
                new MovimientoAlimento();

        movimiento.setTipoMovimiento(
                datos.getTipoMovimiento());

        movimiento.setCantidad(datos.getCantidad());

        ProductoDonado producto = new ProductoDonado();
        producto.setId(datos.getProductoId());

        movimiento.setProducto(producto);

        MovimientoAlimento guardado =
                movimientoService.registrarMovimiento(movimiento);

        return convertirDTO(guardado);
    }
}
