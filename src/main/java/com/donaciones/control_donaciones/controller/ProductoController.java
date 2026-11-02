
package com.donaciones.control_donaciones.controller;

import com.donaciones.control_donaciones.dto.ProductoDTO;
import com.donaciones.control_donaciones.entity.ProductoDonado;
import com.donaciones.control_donaciones.service.ProductoService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;



    private ProductoDTO convertirDTO(ProductoDonado producto) {
        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getCategoria(),
                producto.getStockActual(),
                producto.getActivo()
        );
    }



    @GetMapping
    public List<ProductoDTO> listar() {
        return productoService.listarProductos()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }



    @GetMapping("/disponibles")
    public List<ProductoDTO> disponibles() {
        return productoService.productosDisponibles()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }



    @GetMapping("/stock-bajo")
    public List<ProductoDTO> stockBajo() {
        return productoService.productosStockBajo()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }



    @GetMapping("/{id}")
    public ProductoDTO buscar(@PathVariable Long id) {
        return convertirDTO(productoService.buscarPorId(id));
    }



    @PostMapping
    public ProductoDTO registrar(
            @Valid @RequestBody ProductoDTO datos) {

        ProductoDonado producto = new ProductoDonado();

        producto.setNombre(datos.getNombre());
        producto.setCategoria(datos.getCategoria());

        ProductoDonado guardado =
                productoService.guardarProducto(producto);

        return convertirDTO(guardado);
    }




    @PutMapping("/{id}")
    public ProductoDTO actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoDTO datos) {

        ProductoDonado producto = new ProductoDonado();

        producto.setNombre(datos.getNombre());
        producto.setCategoria(datos.getCategoria());

        ProductoDonado actualizado =
                productoService.actualizarProducto(id, producto);

        return convertirDTO(actualizado);
    }



    @PatchMapping("/{id}/desactivar")
    public ProductoDTO desactivar(@PathVariable Long id) {

        ProductoDonado desactivado =
                productoService.desactivarProducto(id);

        return convertirDTO(desactivado);
    }
}
