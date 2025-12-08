package com.starkindustries.casopracticotema4.model;

import lombok.Data; // Si usas Lombok
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "notificaciones")
public class Notificacion {
    @Id
    private String id;
    private String usuario;
    private String mensaje;
    private String tipo; // "INFO", "ALERTA", "URGENTE"
    private LocalDateTime fecha;
    private Boolean leido;

    public Notificacion() {
        this.fecha = LocalDateTime.now();
        this.leido = false;
    }
}