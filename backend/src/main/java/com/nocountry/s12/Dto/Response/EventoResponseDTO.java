package com.nocountry.s12.Dto.Response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nocountry.s12.models.Evento;

public record EventoResponseDTO(
    Long idEvento,
    String titulo,
    String lugar,
    String hora,
    Double precio,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate fechaEvento,
    String descripcion
) {
    public EventoResponseDTO(Evento evento){
        this(evento.getId(), evento.getTitulo(), evento.getLugar(), 
        evento.getHora(), evento.getPrecio(), evento.getFechaEvento(), evento.getDescripcion()); 
    }
}