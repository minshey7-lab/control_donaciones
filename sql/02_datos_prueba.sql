
-- NUTRIVA - PF-30
-- Datos de prueba
-- Ejecutar solamente en una base de datos de prueba vacía

-- 4 donantes
INSERT INTO donantes (nombre, telefono, tipo, direccion)
VALUES
    ('Ana Lopez', '12345678', 'PERSONA', 'Guatemala'),
    ('Fundacion Esperanza', '87654321', 'ORGANIZACION', 'Mixco'),
    ('Carlos Perez', '23456789', 'PERSONA', 'Villa Nueva'),
    ('Asociacion Solidaria', '34567890', 'ORGANIZACION', 'Amatitlan');

-- 10 productos
INSERT INTO productos (nombre, categoria, stock_actual)
VALUES
    ('Arroz', 'Granos', 10),
    ('Frijol', 'Granos', 15),
    ('Aceite', 'Basicos', 8),
    ('Azucar', 'Basicos', 12),
    ('Sal', 'Condimentos', 20),
    ('Pasta', 'Granos', 9),
    ('Leche', 'Lacteos', 6),
    ('Avena', 'Cereales', 11),
    ('Harina', 'Basicos', 7),
    ('Lentejas', 'Granos', 13);

-- Donaciones de ejemplo
INSERT INTO donaciones (
    descripcion_alimento,
    cantidad,
    unidad_medida,
    fecha_donacion,
    fecha_caducidad,
    estado,
    donante_id
)
SELECT
    'Arroz', 10, 'UNIDADES',
    DATE '2026-11-01',
    DATE '2027-05-01',
    'RECIBIDA',
    id
FROM donantes
WHERE nombre = 'Ana Lopez'
ORDER BY id DESC
LIMIT 1;

INSERT INTO donaciones (
    descripcion_alimento,
    cantidad,
    unidad_medida,
    fecha_donacion,
    fecha_caducidad,
    estado,
    donante_id
)
SELECT
    'Frijol', 15, 'UNIDADES',
    DATE '2026-11-01',
    DATE '2027-05-01',
    'RECIBIDA',
    id
FROM donantes
WHERE nombre = 'Fundacion Esperanza'
ORDER BY id DESC
LIMIT 1;

-- Movimientos de ejemplo
INSERT INTO movimientos (
    tipo_movimiento, cantidad, fecha, producto_id
)
SELECT 'ENTRADA', 10, CURRENT_TIMESTAMP, id
FROM productos
WHERE nombre = 'Arroz'
ORDER BY id DESC
LIMIT 1;

INSERT INTO movimientos (
    tipo_movimiento, cantidad, fecha, producto_id
)
SELECT 'ENTRADA', 15, CURRENT_TIMESTAMP, id
FROM productos
WHERE nombre = 'Frijol'
ORDER BY id DESC
LIMIT 1;
