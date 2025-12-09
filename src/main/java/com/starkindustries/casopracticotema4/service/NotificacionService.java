package com.starkindustries.casopracticotema4.service;

import com.starkindustries.casopracticotema4.model.Notificacion;
import com.starkindustries.casopracticotema4.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository repository;
    private final Sinks.Many<Notificacion> sink = Sinks.many().multicast().onBackpressureBuffer();

    // 1. Obtener flujo: Histórico + Nuevos eventos (SSE)
    public Flux<Notificacion> getNotificacionesEnTiempoReal(String usuario) {
        return repository.findByUsuario(usuario)
                .concatWith(sink.asFlux().filter(n -> n.getUsuario().equals(usuario)));
    }

    // 2. Guardar y Emitir evento
    public Mono<Notificacion> addNotificacion(Notificacion n) {
        if (n.getFecha() == null) n.setFecha(java.time.LocalDateTime.now());
        n.setLeido(false);

        return repository.save(n)
                .doOnSuccess(saved -> sink.tryEmitNext(saved)); // ¡Aquí notificamos al SSE!
    }

    // 3. Marcar como leído
    public Mono<Notificacion> marcarLeido(String id) {
        return repository.findById(id)
                .flatMap(n -> {
                    n.setLeido(true);
                    return repository.save(n);
                });
    }

    // 4. Filtrar
    public Flux<Notificacion> filtrarPorTipo(String usuario, String tipo) {
        return repository.findByUsuarioAndTipo(usuario, tipo);
    }

    // 5. Eliminar
    public Mono<Void> eliminarNotificacion(String id) {
        return repository.deleteById(id);
    }
}