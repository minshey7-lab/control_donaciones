
package com.donaciones.control_donaciones;

import com.donaciones.control_donaciones.entity.MovimientoAlimento;
import com.donaciones.control_donaciones.entity.ProductoDonado;
import com.donaciones.control_donaciones.repository.MovimientoRepository;
import com.donaciones.control_donaciones.repository.ProductoRepository;
import com.donaciones.control_donaciones.service.MovimientoService;
import com.donaciones.control_donaciones.service.ProductoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControlDonacionesApplicationTests {

	@Mock
	private ProductoRepository productoRepository;

	@Mock
	private MovimientoRepository movimientoRepository;

	@InjectMocks
	private MovimientoService movimientoService;

	@InjectMocks
	private ProductoService productoService;

	private ProductoDonado producto;

	@BeforeEach
	void prepararDatos() {
		producto = new ProductoDonado();
		producto.setId(1L);
		producto.setNombre("Arroz");
		producto.setCategoria("Granos");
		producto.setStockActual(10);
	}

	@Test
	void noPermitirCantidadNegativa() {

		MovimientoAlimento movimiento = new MovimientoAlimento();
		movimiento.setTipoMovimiento("ENTRADA");
		movimiento.setCantidad(-5);
		movimiento.setProducto(producto);

		assertThrows(IllegalArgumentException.class, () -> {
			movimientoService.registrarMovimiento(movimiento);
		});

		verifyNoInteractions(movimientoRepository);
	}

	@Test
	void noPermitirTipoInvalido() {

		MovimientoAlimento movimiento = new MovimientoAlimento();
		movimiento.setTipoMovimiento("CAMBIO");
		movimiento.setCantidad(5);
		movimiento.setProducto(producto);

		assertThrows(IllegalArgumentException.class, () -> {
			movimientoService.registrarMovimiento(movimiento);
		});

		verifyNoInteractions(movimientoRepository);
	}

	@Test
	void noPermitirSalidaSinStock() {

		when(productoRepository.findById(1L))
				.thenReturn(Optional.of(producto));

		MovimientoAlimento movimiento = new MovimientoAlimento();
		movimiento.setTipoMovimiento("SALIDA");
		movimiento.setCantidad(20);
		movimiento.setProducto(producto);

		assertThrows(IllegalArgumentException.class, () -> {
			movimientoService.registrarMovimiento(movimiento);
		});

		assertEquals(10, producto.getStockActual());
		verifyNoInteractions(movimientoRepository);
	}

	@Test
	void noPermitirProductoSinNombre() {

		ProductoDonado nuevoProducto = new ProductoDonado();
		nuevoProducto.setNombre("");
		nuevoProducto.setCategoria("Granos");

		assertThrows(IllegalArgumentException.class, () -> {
			productoService.guardarProducto(nuevoProducto);
		});

		verifyNoInteractions(productoRepository);
	}

	@Test
	void registrarEntradaCorrectamente() {

		when(productoRepository.findById(1L))
				.thenReturn(Optional.of(producto));

		when(movimientoRepository.save(any(MovimientoAlimento.class)))
				.thenAnswer(invocacion -> invocacion.getArgument(0));

		MovimientoAlimento movimiento = new MovimientoAlimento();
		movimiento.setTipoMovimiento("ENTRADA");
		movimiento.setCantidad(5);
		movimiento.setProducto(producto);

		movimientoService.registrarMovimiento(movimiento);

		assertEquals(15, producto.getStockActual());

		verify(movimientoRepository).save(movimiento);
	}
}
