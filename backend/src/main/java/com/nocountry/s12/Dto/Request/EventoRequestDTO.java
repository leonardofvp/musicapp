package com.nocountry.s12.Dto.Request;


import com.fasterxml.jackson.annotation.JsonFormat;



public record EventoRequestDTO(
    Long idEvento,
    String titulo,
    String lugar,
    String hora,
    Double precio,
    @JsonFormat(pattern = "yyyy-MM-dd")
    String fechaEvento,
    String descripcion
){
    // public EventoRequestDTO(Evento evento){
    //     this(evento.getId(), evento.getTitulo(), evento.getLugar(), 
    //     evento.getHora(), evento.getPrecio(), evento.getFechaEvento());
    // }

}
