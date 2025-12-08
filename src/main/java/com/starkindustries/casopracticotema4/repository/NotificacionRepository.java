package com.starkindustries.casopracticotema4.repository;

import com.starkindustries.casopracticotema4.model.Notificacion;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface NotificacionRepository extends ReactiveMongoRepository<Notificacion, String> {
    // Buscar notificaciones por usuario
    Flux<Notificacion> findByUsuario(String usuario);

    // Filtrar por usuario y tipo
    Flux<Notificacion> findByUsuarioAndTipo(String usuario, String tipo);
}