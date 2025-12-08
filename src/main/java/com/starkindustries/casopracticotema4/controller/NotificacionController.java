package com.starkindustries.casopracticotema4.controller;

import com.starkindustries.casopracticotema4.model.Notificacion;
import com.starkindustries.casopracticotema4.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller // Usamos @Controller para devolver vistas Thymeleaf
public class NotificacionController {

    @Autowired
    private NotificacionService service;

    // --- VISTAS (Thymeleaf) ---

    @GetMapping("/notificaciones/{usuario}")
    public String verNotificaciones(@PathVariable String usuario, Model model) {
        model.addAttribute("usuario", usuario);
        // Inicializamos la vista. Los datos cargarán por SSE o AJAX.
        return "lista_notificaciones";
    }

    // --- API REACTIVA (SSE y CRUD) ---

    // SSE: El navegador se suscribe aquí para recibir datos
    @GetMapping(value = "/sse/notificaciones/{usuario}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<Notificacion> streamNotificaciones(@PathVariable String usuario) {
        return service.getNotificacionesEnTiempoReal(usuario);
    }

    // Crear notificación (Para probar con Postman o un form)
    @PostMapping("/api/notificaciones")
    @ResponseBody
    public Mono<Notificacion> crear(@RequestBody Notificacion n) {
        return service.addNotificacion(n);
    }

    // Marcar como leído
    @PostMapping("/api/notificaciones/{id}/leer")
    @ResponseBody
    public Mono<Notificacion> marcarLeer(@PathVariable String id) {
        return service.marcarLeido(id);
    }

    // Eliminar
    @DeleteMapping("/api/notificaciones/{id}")
    @ResponseBody
    public Mono<Void> eliminar(@PathVariable String id) {
        return service.eliminarNotificacion(id);
    }
}