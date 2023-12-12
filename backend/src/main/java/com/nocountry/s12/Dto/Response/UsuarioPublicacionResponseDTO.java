package com.nocountry.s12.Dto.Response;

import com.nocountry.s12.models.Imagen;
import com.nocountry.s12.models.Usuario;

public record UsuarioPublicacionResponseDTO(
        Long id,
        String username,
        Imagen fotoPerfil
) {
    public UsuarioPublicacionResponseDTO(Usuario usuario){
        this(usuario.getId(), usuario.getUsername(), usuario.getFotoPerfil());
    }
}
