package com.nocountry.s12.Dto.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nocountry.s12.models.Imagen;
import com.nocountry.s12.models.Publicacion;

import java.time.LocalDate;

public record PublicacionResponseDTO(

        Long id,
        String mensaje,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate fechaCreacion,
        Imagen imagen,
        UsuarioPublicacionResponseDTO usuario
) {
    public PublicacionResponseDTO(Publicacion publicacion){
        this(publicacion.getId(), publicacion.getMensaje(), publicacion.getFechaCreacion(), publicacion.getImagen(), new UsuarioPublicacionResponseDTO(
                publicacion.getUsuario().getId(),
                publicacion.getUsuario().getUsername(),
                publicacion.getUsuario().getFotoPerfil()
        ));
    }
}
