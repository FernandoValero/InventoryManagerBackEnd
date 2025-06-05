# 🛍️ Inventory Service

Este microservicio gestiona el inventario de productos, las operaciones de ventas, la relación con proveedores, usuarios y clientes.

## Tecnologías utilizadas
- Java 17
- Spring Boot
- Spring Data JPA
- Spring Web
- Spring Cloud (Eureka Client, Config Client)
- Feign
- MySQL
- Swagger/OpenAPI

## Funcionalidades
- Gestión CRUD de productos, proveedores, usuarios y clientes.
- Registro de ventas y actualizaciones de stock.
- Exposición de endpoints RESTfull.
- Comunicación con otros servicios a través de Feign Client.
- Registro en el servidor de descubrimiento Eureka.

## 📚 Documentación Swagger

Disponible al iniciar el servicio: `http://localhost:8080/swagger-ui/index.html`


## Endpoints principales
## 📦 Proveedores

| Método | Endpoint             | Descripción                     |
|--------|----------------------|---------------------------------|
| GET    | `/suppliers/{id}`    | Obtener proveedor por ID        |
| PUT    | `/suppliers/{id}`    | Actualizar proveedor existente  |
| DELETE | `/suppliers/{id}`    | Eliminar proveedor              |
| GET    | `/suppliers`         | Obtener todos los proveedores   |
| POST   | `/suppliers`         | Crear nuevo proveedor           |

---

## 🧾 Ventas

| Método | Endpoint                          | Descripción                         |
|--------|-----------------------------------|-------------------------------------|
| GET    | `/sales`                          | Obtener todas las ventas            |
| POST   | `/sales`                          | Crear nueva venta                   |
| GET    | `/sales/{id}`                     | Obtener venta por ID                |
| DELETE | `/sales/{id}`                     | Eliminar venta                      |
| GET    | `/sales/year`                     | Obtener ventas por año              |
| GET    | `/sales/user/{userId}`            | Obtener ventas por usuario          |
| GET    | `/sales/product/{productId}`      | Obtener ventas por producto         |
| GET    | `/sales/month`                    | Obtener ventas por mes              |
| GET    | `/sales/client/{clientId}`        | Obtener ventas por cliente          |
| GET    | `/sales/between`                  | Obtener ventas entre fechas         |

---

## 🛒 Productos

| Método | Endpoint             | Descripción                    |
|--------|----------------------|--------------------------------|
| GET    | `/products/{id}`     | Obtener producto por ID        |
| PUT    | `/products/{id}`     | Actualizar producto existente  |
| DELETE | `/products/{id}`     | Eliminar producto              |
| GET    | `/products`          | Obtener todos los productos    |
| POST   | `/products`          | Crear nuevo producto           |

---

## 👥 Clientes

| Método | Endpoint           | Descripción                     |
|--------|--------------------|---------------------------------|
| GET    | `/clients/{id}`    | Obtener cliente por ID          |
| PUT    | `/clients/{id}`    | Actualizar cliente existente    |
| DELETE | `/clients/{id}`    | Eliminar cliente                |
| GET    | `/clients`         | Obtener todos los clientes      |
| POST   | `/clients`         | Crear nuevo cliente             |

---

## 🧑‍💻 Usuarios

| Método | Endpoint          | Descripción                     |
|--------|-------------------|---------------------------------|
| GET    | `/users/{id}`     | Obtener usuario por ID          |
| PUT    | `/users/{id}`     | Actualizar usuario existente    |
| DELETE | `/users/{id}`     | Eliminar usuario                |
| GET    | `/users`          | Obtener todos los usuarios      |
| POST   | `/users`          | Crear nuevo usuario             |


---

## Configuración

Este servicio se conecta a un servidor de configuración centralizado. Las propiedades necesarias se obtienen automáticamente desde **Spring Cloud Config Server**.


## 🏁 Ejecución local

Asegúrate de que los servicios de Eureka y Config Server estén corriendo.


## 🖼️ Arquitectura


                     +--------------------+
                     |  API Gateway       |
                     +--------------------+
                               |
                 +-------------+--------------+
                 |                            |
       +------------------+         +------------------+
       |  InventoryService| <-----> |  ReportService   |
       +------------------+         +------------------+
                 |
         +---------------+
         |  MySQL DB     |
         +---------------+

