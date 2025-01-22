# Arquitectura de Microservicios con Spring Boot

Este repositorio contiene tres microservicios independientes diseñados para gestionar usuarios, productos y pedidos. Cada microservicio se implementa utilizando Spring Boot y sigue una arquitectura modular para manejar responsabilidades específicas.

---

## Overview
El proyecto implementa tres microservicios con las siguientes responsabilidades:

- **Microservicio de Usuarios**: Administra usuarios y sus roles.
- **Microservicio de Productos**: Gestiona el catálogo de productos e inventario.
- **Microservicio de Pedidos**: Administra pedidos y sus estados.

Cada microservicio utiliza una base de datos en memoria H2 para el almacenamiento temporal de datos y expone sus funcionalidades mediante APIs REST.

---

## Endpoints

### Microservicio de Usuarios
- `GET /users` - Obtener todos los usuarios.
- `POST /users` - Crear un nuevo usuario.
- `GET /roles` - Obtener todos los roles.

### Microservicio de Productos
- `GET /products` - Obtener todos los productos.
- `POST /products` - Crear un nuevo producto.
- `PUT /products/{id}` - Actualizar el stock de un producto.

### Microservicio de Pedidos
- `POST /orders` - Crear un nuevo pedido.
- `GET /orders` - Obtener todos los pedidos.
- `PUT /orders/{id}` - Actualizar el estado de un pedido.
- `POST /orders/{id}/new-item` - Agregar un nuevo ítem a un pedido existente.

---

## Tecnología
- **Spring Boot**: Para el desarrollo de las aplicaciones.
- **Spring Data JPA**: Para el acceso y manejo de datos.
- **Spring Web**: Para la creación de APIs REST.
- **H2 Database**: Base de datos en memoria para pruebas y desarrollo.

