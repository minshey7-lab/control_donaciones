# NUTRIVA – Control de Donaciones de Alimentos

**Código del proyecto:** PF-30  
**Estudiante:** SHEILA ALESANDRA GABRIEL OROZCO 
**Carné:** 9941 -25-1795
**Curso:** Programacion II

## 1. Descripción del proyecto

NUTRIVA es un sistema web que permite administrar donaciones de alimentos, registrar donantes y controlar las entradas y salidas del inventario.

El proyecto busca facilitar la organización de los alimentos donados, evitar salidas superiores a las existencias disponibles y proporcionar información actualizada sobre los productos.

## 2. Tecnologías utilizadas

- Java y Spring Boot
- Maven
- PostgreSQL
- Spring Data JPA
- Spring Validation
- HTML, CSS y JavaScript
- JUnit 5 y Mockito
- Postman

## 3. Estructura del sistema

El proyecto utiliza una arquitectura por capas:

- **controller:** recibe las solicitudes HTTP y define los endpoints.
- **service:** contiene la lógica del negocio y las validaciones.
- **repository:** realiza las operaciones con PostgreSQL mediante JPA.
- **entity:** representa las entidades de la base de datos.
- **dto:** define los datos utilizados en las solicitudes y los indicadores.
- **exception:** administra los errores de la aplicación.
- **static:** contiene los archivos HTML, CSS y JavaScript.
- **sql:** contiene los scripts de creación de tablas y datos de prueba.

## 4. Funcionalidades principales

- Registrar y consultar donantes.
- Registrar y consultar donaciones.
- Registrar, consultar y modificar productos.
- Registrar entradas y salidas de alimentos.
- Consultar productos disponibles y con existencias bajas.
- Filtrar movimientos por tipo.
- Visualizar indicadores de unidades recibidas y entregadas.

## 5. Reglas del negocio

- Las cantidades registradas deben ser mayores que cero.
- Los movimientos únicamente pueden ser `ENTRADA` o `SALIDA`.
- No se permiten salidas superiores a las existencias disponibles.
- Cada movimiento debe estar asociado a un producto existente.
- Los productos deben tener nombre y categoría.
- Las donaciones deben estar asociadas a un donante existente.
- La fecha de caducidad no puede ser anterior a la fecha de donación.
- Las entradas aumentan las existencias y las salidas las disminuyen.

## 6. Base de datos y configuración

El sistema utiliza PostgreSQL.

**Base de datos:** `db_donaciones_alimentos`

**Tablas:** `donantes`, `donaciones`, `productos` y `movimientos`.

Los scripts se encuentran en la carpeta `sql`:

- `01_crear_tablas.sql`
- `02_datos_prueba.sql`

Para configurar la conexión, se utilizan las siguientes variables de entorno:

- `DB_HOST`: servidor de PostgreSQL, normalmente `localhost`.
- `DB_PORT`: puerto, normalmente `5432`.
- `DB_NAME`: nombre de la base de datos.
- `DB_USERNAME`: usuario de PostgreSQL.
- `DB_PASSWORD`: contraseña local de PostgreSQL.

La contraseña debe configurarse en el entorno de ejecución y no debe publicarse en GitHub.

## 7. Requisitos de ejecución

- Java JDK compatible con el proyecto.
- PostgreSQL instalado y funcionando.
- IntelliJ IDEA u otro entorno compatible.
- Maven Wrapper incluido en el proyecto.
- Navegador web.

## 8. Instrucciones de ejecución

1. Abrir el proyecto `control_donaciones` en IntelliJ IDEA.
2. Crear la base de datos `db_donaciones_alimentos` en PostgreSQL, si todavía no existe.
3. Configurar las variables de entorno de la conexión.
4. Ejecutar la clase principal `ControlDonacionesApplication.java`.
5. Esperar a que Spring Boot inicie correctamente en el puerto 8080.
6. Abrir el frontend desde el navegador:

   `http://localhost:8080/`

El frontend se encuentra en `src/main/resources/static` y se comunica con el backend mediante solicitudes HTTP.

## 9. Endpoints principales

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/productos` | Consultar productos |
| GET | `/api/productos/{id}` | Buscar producto por ID |
| POST | `/api/productos` | Registrar producto |
| PUT | `/api/productos/{id}` | Modificar producto |
| GET | `/api/productos/disponibles` | Consultar productos disponibles |
| GET | `/api/productos/stock-bajo` | Consultar productos con existencias bajas |
| GET | `/api/donantes` | Consultar donantes |
| POST | `/api/donantes` | Registrar donante |
| DELETE | `/api/donantes/{id}` | Eliminar donante |
| GET | `/api/donaciones` | Consultar donaciones |
| POST | `/api/donaciones` | Registrar donación |
| DELETE | `/api/donaciones/{id}` | Eliminar donación |
| GET | `/api/movimientos` | Consultar movimientos |
| POST | `/api/movimientos` | Registrar entrada o salida |
| GET | `/api/movimientos/tipo/{tipo}` | Filtrar movimientos |
| GET | `/api/movimientos/indicadores` | Consultar indicadores |

## 10. Pruebas realizadas

Se utilizaron Postman, JUnit 5 y Mockito para comprobar el funcionamiento de la API y sus validaciones.

Las pruebas automáticas verifican cantidades negativas, movimientos inválidos, salidas sin existencias suficientes, productos sin nombre y entradas correctas al inventario.

**Resultado de las pruebas automáticas: 5 aprobadas y 0 fallidas.**

---

**Proyecto Final PF-30 – NUTRIVA**  
**Control de Donaciones de Alimentos**