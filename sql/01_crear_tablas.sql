
-- NUTRIVA
-- Script de creación de tablas
-- Base de datos: db_donaciones_alimentos

CREATE TABLE IF NOT EXISTS donantes (
                                        id BIGSERIAL PRIMARY KEY,
                                        nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    direccion VARCHAR(150) NOT NULL
    );


CREATE TABLE IF NOT EXISTS productos (
                                         id BIGSERIAL PRIMARY KEY,
                                         nombre VARCHAR(255) NOT NULL,
    categoria VARCHAR(255) NOT NULL,
    stock_actual INTEGER NOT NULL DEFAULT 0,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT chk_productos_stock
    CHECK (stock_actual >= 0)
    );


CREATE TABLE IF NOT EXISTS donaciones (
                                          id BIGSERIAL PRIMARY KEY,
                                          descripcion_alimento VARCHAR(150) NOT NULL,
    cantidad DOUBLE PRECISION NOT NULL,
    unidad_medida VARCHAR(20) NOT NULL,
    fecha_donacion DATE NOT NULL,
    fecha_caducidad DATE NOT NULL,
    estado VARCHAR(30) NOT NULL,
    donante_id BIGINT NOT NULL,
    CONSTRAINT fk_donacion_donante
    FOREIGN KEY (donante_id)
    REFERENCES donantes(id)
    );

CREATE TABLE IF NOT EXISTS movimientos (
                                           id BIGSERIAL PRIMARY KEY,
                                           tipo_movimiento VARCHAR(255),
    cantidad INTEGER,
    fecha TIMESTAMP,
    producto_id BIGINT,
    CONSTRAINT fk_movimiento_producto
    FOREIGN KEY (producto_id)
    REFERENCES productos(id)
    );
