
package com.donaciones.control_donaciones.service;

import com.donaciones.control_donaciones.entity.ProductoDonado;
import com.donaciones.control_donaciones.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.donaciones.control_donaciones.exception.RecursoNoEncontradoException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;






    public List<ProductoDonado> listarProductos() {
        return productoRepository.findAll();
    }




    public List<ProductoDonado> productosDisponibles() {
        return productoRepository.findAll()
                .stream()
                .filter(producto ->
                        Boolean.TRUE.equals(producto.getActivo())
                                && producto.getStockActual() != null
                                && producto.getStockActual() > 0)
                .toList();
    }




    public List<ProductoDonado> productosStockBajo() {
        return productoRepository.findAll()
                .stream()
                .filter(producto ->
                        Boolean.TRUE.equals(producto.getActivo())
                                && producto.getStockActual() != null
                                && producto.getStockActual() > 0
                                && producto.getStockActual() <= 5)
                .toList();
    }


    public ProductoDonado buscarPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "Producto no encontrado"));
    }





    @Transactional
    public ProductoDonado guardarProducto(ProductoDonado producto) {

        validarProducto(producto);

        producto.setId(null);
        producto.setNombre(producto.getNombre().trim());
        producto.setCategoria(producto.getCategoria().trim());
        producto.setStockActual(0);
        producto.setActivo(true);

        return productoRepository.save(producto);
    }




    @Transactional
    public ProductoDonado actualizarProducto(
            Long id, ProductoDonado datos) {

        validarProducto(datos);

        ProductoDonado producto = buscarPorId(id);

        if (!Boolean.TRUE.equals(producto.getActivo())) {
            throw new IllegalArgumentException(
                    "No se puede modificar un producto desactivado");
        }

        producto.setNombre(datos.getNombre().trim());
        producto.setCategoria(datos.getCategoria().trim());

        return productoRepository.save(producto);
    }


    @Transactional
    public ProductoDonado desactivarProducto(Long id) {

        ProductoDonado producto = buscarPorId(id);

        if (!Boolean.TRUE.equals(producto.getActivo())) {
            throw new IllegalArgumentException(
                    "El producto ya se encuentra desactivado");
        }

        producto.setActivo(false);

        return productoRepository.save(producto);
    }

    private void validarProducto(ProductoDonado producto) {

        if (producto == null) {
            throw new IllegalArgumentException(
                    "Debe ingresar los datos del producto");
        }

        if (producto.getNombre() == null
                || producto.getNombre().isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del producto es obligatorio");
        }

        if (producto.getCategoria() == null
                || producto.getCategoria().isBlank()) {
            throw new IllegalArgumentException(
                    "La categoría del producto es obligatoria");
        }
    }
}
