// NUTRIVA - Interfaz conectada a Spring Boot
const API = '/api';
const $ = id => document.getElementById(id);
let productos = [];
let donantes = [];
let donaciones = [];

function mensaje(id, texto, error = false) {
    const elemento = $(id);
    if (!elemento) return;
    elemento.textContent = texto;
    elemento.style.color = error ? '#B42340' : '#4B2138';
}

async function solicitar(ruta, opciones = {}) {
    let respuesta;
    try {
        respuesta = await fetch(API + ruta, opciones);
    } catch (error) {
        throw new Error('No hay conexión con el servidor. Verifica Spring Boot.');
    }

    const texto = await respuesta.text();
    let datos = null;

    if (texto) {
        try {
            datos = JSON.parse(texto);
        } catch (error) {
            datos = texto;
        }
    }

    if (!respuesta.ok) {
        let detalle = 'Error ' + respuesta.status;
        if (datos && typeof datos === 'object') {
            detalle = datos.error || Object.values(datos).join('. ') || detalle;
        } else if (typeof datos === 'string' && datos.trim()) {
            detalle = datos;
        }
        throw new Error(detalle);
    }

    return datos;
}

function jsonOpciones(metodo, datos) {
    return {
        method: metodo,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(datos)
    };
}

function crearBoton(texto, accion, clase = '') {
    const boton = document.createElement('button');
    boton.type = 'button';
    boton.textContent = texto;
    boton.className = 'boton ' + clase;
    boton.style.margin = '3px';
    boton.addEventListener('click', accion);
    return boton;
}

function agregarCelda(fila, valor) {
    const celda = document.createElement('td');
    celda.textContent = valor == null ? '' : String(valor);
    fila.appendChild(celda);
    return celda;
}

function agregarAcciones(fila, botones) {
    const celda = document.createElement('td');
    botones.forEach(boton => celda.appendChild(boton));
    fila.appendChild(celda);
}

function asegurarColumnaAcciones(tablaId) {
    const tabla = $(tablaId);
    if (!tabla) return;
    const encabezado = tabla.closest('table')?.querySelector('thead tr');
    if (encabezado && !Array.from(encabezado.children)
        .some(celda => celda.textContent.trim() === 'Acciones')) {
        const th = document.createElement('th');
        th.textContent = 'Acciones';
        encabezado.appendChild(th);
    }
}

function mostrarSeccion(seccion) {
    const secciones = [
        'seccionProductos', 'seccionMovimientos',
        'seccionEditarProducto', 'seccionDonantes',
        'seccionDonaciones'
    ];

    secciones.forEach(id => {
        if ($(id)) $(id).style.display = 'none';
    });

    document.querySelectorAll('.menu button').forEach(boton => {
        boton.classList.remove('activo');
    });

    const botones = {
        inicio: 'botonInicio',
        productos: 'botonProductos',
        inventario: 'botonInventario',
        movimientos: 'botonMovimientos',
        donantes: 'botonDonantes',
        donaciones: 'botonDonaciones'
    };

    if ($(botones[seccion])) $(botones[seccion]).classList.add('activo');

    if (seccion === 'productos') {
        $('seccionProductos').style.display = 'block';
        $('seccionEditarProducto').style.display = 'block';
        $('seccionProductos').scrollIntoView({ behavior: 'smooth' });
    } else if (seccion === 'inventario') {
        $('seccionInventario').scrollIntoView({ behavior: 'smooth' });
    } else if (seccion !== 'inicio') {
        const id = {
            movimientos: 'seccionMovimientos',
            donantes: 'seccionDonantes',
            donaciones: 'seccionDonaciones'
        }[seccion];

        if ($(id)) {
            $(id).style.display = 'block';
            $(id).scrollIntoView({ behavior: 'smooth' });
        }
    } else {
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }
}

async function cargarDatos() {
    try {
        productos = await solicitar('/productos');
        const indicadores = await solicitar('/movimientos/indicadores');

        $('totalProductos').textContent = productos.length;
        $('unidadesDisponibles').textContent = productos.reduce(
            (total, producto) => total + (producto.activo === false ? 0 : (producto.stockActual || 0)), 0
        );
        $('unidadesRecibidas').textContent = indicadores.totalUnidadesRecibidas ?? 0;
        $('unidadesEntregadas').textContent = indicadores.totalUnidadesEntregadas ?? 0;

        mostrarProductos();
        cargarSelectoresProductos();
        mensaje('mensajeError', '');
    } catch (error) {
        mensaje('mensajeError', error.message, true);
    }
}

function mostrarProductos() {
    const tabla = $('tablaProductos');
    tabla.innerHTML = '';
    asegurarColumnaAcciones('tablaProductos');

    if (productos.length === 0) {
        const fila = document.createElement('tr');
        const celda = agregarCelda(fila, 'No hay productos registrados.');
        celda.colSpan = 5;
        tabla.appendChild(fila);
        return;
    }

    productos.forEach(producto => {
        const fila = document.createElement('tr');
        agregarCelda(fila, producto.nombre);
        agregarCelda(fila, producto.categoria);
        agregarCelda(fila, producto.stockActual ?? 0);

        let estado = 'Disponible';
        if (producto.activo === false) estado = 'Desactivado';
        else if (!producto.stockActual) estado = 'Sin existencias';
        else if (producto.stockActual <= 5) estado = 'Stock bajo';

        agregarCelda(fila, estado);

        const botones = [];
        if (producto.activo !== false) {
            botones.push(crearBoton('Desactivar', () => desactivarProducto(producto.id)));
        }
        agregarAcciones(fila, botones);
        tabla.appendChild(fila);
    });
}

function cargarSelectoresProductos() {
    const movimiento = $('productoMovimiento');
    const editar = $('productoEditar');

    if (movimiento) {
        movimiento.innerHTML = '<option value="">Selecciona un producto</option>';
    }
    if (editar) {
        editar.innerHTML = '<option value="">Selecciona un producto</option>';
    }

    productos.filter(p => p.activo !== false).forEach(producto => {
        if (movimiento) {
            const opcion = document.createElement('option');
            opcion.value = producto.id;
            opcion.textContent = producto.nombre + ' (Stock: ' + (producto.stockActual ?? 0) + ')';
            movimiento.appendChild(opcion);
        }

        if (editar) {
            const opcion = document.createElement('option');
            opcion.value = producto.id;
            opcion.textContent = producto.nombre;
            editar.appendChild(opcion);
        }
    });
}

async function desactivarProducto(id) {
    if (!confirm('¿Deseas desactivar este producto?')) return;
    try {
        await solicitar('/productos/' + id + '/desactivar', { method: 'PATCH' });
        await cargarDatos();
        mensaje('mensajeError', 'Producto desactivado correctamente.');
    } catch (error) {
        mensaje('mensajeError', error.message, true);
    }
}

async function cargarDonantes() {
    try {
        donantes = await solicitar('/donantes');
        const tabla = $('tablaDonantes');
        tabla.innerHTML = '';
        asegurarColumnaAcciones('tablaDonantes');

        if (donantes.length === 0) {
            const fila = document.createElement('tr');
            const celda = agregarCelda(fila, 'No hay donantes registrados.');
            celda.colSpan = 5;
            tabla.appendChild(fila);
            return;
        }

        donantes.forEach(donante => {
            const fila = document.createElement('tr');
            agregarCelda(fila, donante.nombre);
            agregarCelda(fila, donante.telefono);
            agregarCelda(fila, donante.tipo);
            agregarCelda(fila, donante.direccion);
            agregarAcciones(fila, [
                crearBoton('Editar', () => editarDonante(donante)),
                crearBoton('Eliminar', () => eliminarDonante(donante.id))
            ]);
            tabla.appendChild(fila);
        });
        mensaje('mensajeDonante', '');
    } catch (error) {
        mensaje('mensajeDonante', error.message, true);
    }
}

async function editarDonante(donante) {
    const nombre = prompt('Nombre del donante:', donante.nombre);
    if (nombre === null) return;
    const telefono = prompt('Teléfono:', donante.telefono);
    if (telefono === null) return;
    const tipo = prompt('Tipo (PERSONA u ORGANIZACION):', donante.tipo);
    if (tipo === null) return;
    const direccion = prompt('Dirección:', donante.direccion);
    if (direccion === null) return;

    try {
        await solicitar('/donantes/' + donante.id, jsonOpciones('PUT', {
            nombre: nombre.trim(),
            telefono: telefono.trim(),
            tipo: tipo.trim().toUpperCase(),
            direccion: direccion.trim()
        }));
        await cargarDonantes();
        mensaje('mensajeDonante', 'Donante actualizado correctamente.');
    } catch (error) {
        mensaje('mensajeDonante', error.message, true);
    }
}

async function eliminarDonante(id) {
    if (!confirm('¿Seguro que deseas eliminar este donante?')) return;
    try {
        await solicitar('/donantes/' + id, { method: 'DELETE' });
        await cargarDonantes();
        mensaje('mensajeDonante', 'Donante eliminado correctamente.');
    } catch (error) {
        mensaje('mensajeDonante', error.message, true);
    }
}

async function cargarDonaciones() {
    try {
        donaciones = await solicitar('/donaciones');
        donantes = await solicitar('/donantes');
        const tabla = $('tablaDonaciones');
        tabla.innerHTML = '';
        asegurarColumnaAcciones('tablaDonaciones');

        if (donaciones.length === 0) {
            const fila = document.createElement('tr');
            const celda = agregarCelda(fila, 'No hay donaciones registradas.');
            celda.colSpan = 8;
            tabla.appendChild(fila);
            return;
        }

        donaciones.forEach(donacion => {
            const fila = document.createElement('tr');
            const donante = donantes.find(d => d.id === donacion.donanteId);

            [
                donante ? donante.nombre : 'Donante #' + donacion.donanteId,
                donacion.descripcionAlimento,
                donacion.cantidad,
                donacion.unidadMedida,
                donacion.fechaDonacion,
                donacion.fechaCaducidad,
                donacion.estado
            ].forEach(valor => agregarCelda(fila, valor));

            agregarAcciones(fila, [
                crearBoton('Editar', () => editarDonacion(donacion)),
                crearBoton('Eliminar', () => eliminarDonacion(donacion.id))
            ]);
            tabla.appendChild(fila);
        });
        mensaje('mensajeDonacion', '');
    } catch (error) {
        mensaje('mensajeDonacion', error.message, true);
    }
}

function cargarDonantesSelector() {
    const selector = $('donanteDonacion');
    selector.innerHTML = '<option value="">Selecciona un donante</option>';
    donantes.forEach(donante => {
        const opcion = document.createElement('option');
        opcion.value = donante.id;
        opcion.textContent = donante.nombre;
        selector.appendChild(opcion);
    });
}

async function editarDonacion(donacion) {
    const descripcion = prompt('Descripción del alimento:', donacion.descripcionAlimento);
    if (descripcion === null) return;
    const cantidadTexto = prompt('Cantidad:', donacion.cantidad);
    if (cantidadTexto === null) return;
    const unidad = prompt('Unidad de medida:', donacion.unidadMedida);
    if (unidad === null) return;
    const fecha = prompt('Fecha de donación (AAAA-MM-DD):', donacion.fechaDonacion);
    if (fecha === null) return;
    const caducidad = prompt('Fecha de caducidad (AAAA-MM-DD):', donacion.fechaCaducidad);
    if (caducidad === null) return;
    const estado = prompt('Estado (RECIBIDA o PENDIENTE):', donacion.estado);
    if (estado === null) return;
    const donanteTexto = prompt('ID del donante:', donacion.donanteId);
    if (donanteTexto === null) return;

    try {
        await solicitar('/donaciones/' + donacion.id, jsonOpciones('PUT', {
            descripcionAlimento: descripcion.trim(),
            cantidad: Number(cantidadTexto),
            unidadMedida: unidad.trim(),
            fechaDonacion: fecha.trim(),
            fechaCaducidad: caducidad.trim(),
            estado: estado.trim().toUpperCase(),
            donanteId: Number(donanteTexto)
        }));
        await cargarDonaciones();
        mensaje('mensajeDonacion', 'Donación actualizada correctamente.');
    } catch (error) {
        mensaje('mensajeDonacion', error.message, true);
    }
}

async function eliminarDonacion(id) {
    if (!confirm('¿Seguro que deseas eliminar esta donación?')) return;
    try {
        await solicitar('/donaciones/' + id, { method: 'DELETE' });
        await cargarDonaciones();
        mensaje('mensajeDonacion', 'Donación eliminada correctamente.');
    } catch (error) {
        mensaje('mensajeDonacion', error.message, true);
    }
}

document.addEventListener('DOMContentLoaded', async function () {
    $('botonInicio').addEventListener('click', () => mostrarSeccion('inicio'));
    $('botonProductos').addEventListener('click', () => mostrarSeccion('productos'));
    $('botonInventario').addEventListener('click', async () => {
        mostrarSeccion('inventario');
        await cargarDatos();
    });
    $('botonMovimientos').addEventListener('click', () => mostrarSeccion('movimientos'));
    $('botonDonantes').addEventListener('click', async () => {
        mostrarSeccion('donantes');
        await cargarDonantes();
    });
    $('botonDonaciones').addEventListener('click', async () => {
        mostrarSeccion('donaciones');
        await cargarDonantes();
        cargarDonantesSelector();
        await cargarDonaciones();
    });

    $('formularioProducto').addEventListener('submit', async evento => {
        evento.preventDefault();
        try {
            await solicitar('/productos', jsonOpciones('POST', {
                nombre: $('nombreProducto').value.trim(),
                categoria: $('categoriaProducto').value.trim()
            }));
            $('formularioProducto').reset();
            await cargarDatos();
            mensaje('mensajeProducto', 'Producto registrado correctamente.');
        } catch (error) {
            mensaje('mensajeProducto', error.message, true);
        }
    });

    $('productoEditar').addEventListener('change', function () {
        const producto = productos.find(p => p.id === Number(this.value));
        $('nombreEditar').value = producto ? producto.nombre : '';
        $('categoriaEditar').value = producto ? producto.categoria : '';
    });

    $('formularioEditarProducto').addEventListener('submit', async evento => {
        evento.preventDefault();
        try {
            await solicitar('/productos/' + $('productoEditar').value, jsonOpciones('PUT', {
                nombre: $('nombreEditar').value.trim(),
                categoria: $('categoriaEditar').value.trim()
            }));
            $('formularioEditarProducto').reset();
            await cargarDatos();
            mensaje('mensajeEditarProducto', 'Producto actualizado correctamente.');
        } catch (error) {
            mensaje('mensajeEditarProducto', error.message, true);
        }
    });

    $('formularioMovimiento').addEventListener('submit', async evento => {
        evento.preventDefault();
        try {
            await solicitar('/movimientos', jsonOpciones('POST', {
                productoId: Number($('productoMovimiento').value),
                tipoMovimiento: $('tipoMovimiento').value,
                cantidad: Number($('cantidadMovimiento').value)
            }));
            $('formularioMovimiento').reset();
            await cargarDatos();
            mensaje('mensajeMovimiento', 'Movimiento registrado correctamente.');
        } catch (error) {
            mensaje('mensajeMovimiento', error.message, true);
        }
    });

    $('formularioDonante').addEventListener('submit', async evento => {
        evento.preventDefault();
        try {
            await solicitar('/donantes', jsonOpciones('POST', {
                nombre: $('nombreDonante').value.trim(),
                telefono: $('telefonoDonante').value.trim(),
                tipo: $('tipoDonante').value,
                direccion: $('direccionDonante').value.trim()
            }));
            $('formularioDonante').reset();
            await cargarDonantes();
            mensaje('mensajeDonante', 'Donante registrado correctamente.');
        } catch (error) {
            mensaje('mensajeDonante', error.message, true);
        }
    });

    $('formularioDonacion').addEventListener('submit', async evento => {
        evento.preventDefault();
        const fecha = $('fechaDonacion').value;
        const caducidad = $('fechaCaducidad').value;

        if (caducidad < fecha) {
            mensaje('mensajeDonacion', 'La fecha de caducidad no puede ser anterior a la donación.', true);
            return;
        }

        try {
            await solicitar('/donaciones', jsonOpciones('POST', {
                donanteId: Number($('donanteDonacion').value),
                descripcionAlimento: $('descripcionDonacion').value.trim(),
                cantidad: Number($('cantidadDonacion').value),
                unidadMedida: $('unidadDonacion').value,
                fechaDonacion: fecha,
                fechaCaducidad: caducidad,
                estado: $('estadoDonacion').value
            }));
            $('formularioDonacion').reset();
            await cargarDonaciones();
            mensaje('mensajeDonacion', 'Donación registrada correctamente.');
        } catch (error) {
            mensaje('mensajeDonacion', error.message, true);
        }
    });

    await cargarDatos();
});