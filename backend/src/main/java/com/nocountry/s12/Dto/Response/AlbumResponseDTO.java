/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nocountry.s12.Dto.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nocountry.s12.models.Album;
import com.nocountry.s12.models.Cancion;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Admin
 */
public record AlbumResponseDTO(Long id, 
        String genero, 
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate fechaPublicacion, 
        List<Cancion> canciones
        
        ) {
    

    public AlbumResponseDTO(Album album){
        this(album.getId(), album.getGenero(), album.getFechaPublicacion(), album.getCanciones());
    }
}
