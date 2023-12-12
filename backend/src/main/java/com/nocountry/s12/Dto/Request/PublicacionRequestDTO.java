package com.nocountry.s12.Dto.Request;

import com.nocountry.s12.models.Publicacion;

public record PublicacionRequestDTO(
        String mensaje
        //String imagen
) {
    public PublicacionRequestDTO(Publicacion publicacion){
        this(publicacion.getMensaje());
    }
}
