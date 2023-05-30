# Java Spring Bank REST API

## Descripción

Api rest bancaria desarrollada como prueba, que permite la creación, lectura, actualización y eliminación de Clientes y Cuentas, además de la generación de Movimientos de depósito y retiro para cada cuenta, con limitaciones de monto para retiro diario y generación de reportes de movimientos por cliente entre fechas.

## Endpoints

* /cuentas
* /clientes
* /movimientos
* /reportes

**Para ver todos los endpoints con ejemplos ver [endpoints en postman](https://documenter.getpostman.com/view/24027360/2s93m32NzB#intro)**

## Dependencias y tecnologías principales

* Java 17
* Maven
* Spring Boot 3.1.0
* Spring Boot Starters
  * JPA
  * WEB
  * TEST
* MySQL - Runtime
* H2 - Testing
* Lombok
