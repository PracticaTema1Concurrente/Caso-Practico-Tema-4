Gabriel Kaakedjian
Ivan Hidalgo

# Caso Práctico Tema 4: Sistema Reactivo de Notificaciones en Tiempo Real

## INSTRUCCIONES DE ENTREGA:

- El ejercicio debe ser realizado en grupos de 2 y 3 estudiantes. Todos los miembros del grupo deben hacer la entrega con los mismos archivos.
- Se debe incluir un archivo tipo README donde se especifica el nombre de los miembros del grupo y una explicación breve de la lógica de la solución indicando en una línea qué contiene cada archivo de relevancia (sólo aquellos necesarios para entender la solución).
- Para facilitar la subida de archivos es preferible que estos estén comprimidos en un único archivo .zip, .rar, etc.

## Objetivo

El objetivo de esta práctica es que el estudiante:

1. Aprenda a manejar Server-Sent Events (SSE) en Spring WebFlux.

2. Integre una base de datos MongoDB para almacenar y recuperar notificaciones.

3. Genere vistas con Thymeleaf mostrando notificaciones en tiempo real.

4. Comprenda cómo la programación reactiva permite enviar datos de forma continua y concurrente.

## Descripción del problema

Se desea desarrollar un sistema de notificaciones para usuarios de una aplicación web. Cada notificación tiene los siguientes atributos:

- id (String, generado automáticamente por MongoDB)
- usuario (String)
- mensaje (String)
- tipo (String: "INFO", "ALERTA", "URGENTE")
- fecha (Date)
- leido (Boolean)

La aplicación debe permitir:

1. Registrar nuevas notificaciones y guardarlas en MongoDB.
2. Visualizar notificaciones en tiempo real para cada usuario usando SSE.
3. Marcar notificaciones como leídas.
4. Filtrar notificaciones por tipo.
5. Eliminar notificaciones.

## Tareas

1. Configuración del proyecto

Crear proyecto Spring Boot con dependencias:

- spring-boot-starter-webflux
- spring-boot-starter-data-mongodb-reactive
- spring-boot-starter-thymeleaf

Configurar conexión a MongoDB (localhost:27017) y crear la base de datos notificaciones.

2. Definir la entidad Notificacion
3. Crear un ReactiveMongoRepository
4. Servicio reactivo

Métodos principales:

- Flux<Notificacion> getNotificacionesEnTiempoReal(String usuario) → flujo infinito usando SSE.
- Mono<Notificacion> addNotificacion(Notificacion n)
- Mono<Notificacion> marcarLeido(String id)
- Flux<Notificacion> filtrarPorTipo(String usuario, String tipo)
- Mono<Void> eliminarNotificacion(String id)

## Controlador WebFlux

- Endpoint SSE para recibir notificaciones en tiempo real.
- Endpoints para CRUD (Create, Read, Update, Delete) y filtrado.

## Vista con Thymeleaf

- Lista reactiva de notificaciones por usuario.
- Resaltar las no leídas.
- Botones para marcar como leída y eliminar.
- Filtrado por tipo de notificación.
- Ejemplo de integración SSE.

## Ejemplo de salida esperada

## Página /notificaciones/usuario1

- Lista de notificaciones en tiempo real:

## Mensaje	Tipo	Fecha	Leído	Acción
Pedido enviado	INFO	12/11/2025 14:05	❌	[Marcar Leído]
Pago rechazado	ALERTA	12/11/2025 14:07	❌	[Marcar Leído]
Servidor caído	URGENTE	12/11/2025 14:08	❌	[Marcar Leído]
(Las notificaciones nuevas aparecen automáticamente sin recargar la página gracias a SSE.)
