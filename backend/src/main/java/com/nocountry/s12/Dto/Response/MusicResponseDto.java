package com.nocountry.s12.Dto.Response;

import java.util.List;

import com.nocountry.s12.models.Imagen;


public record MusicResponseDto(
		 Long id,
	     String titulo,
	     String genero,
	     String fechaSubida,
	     List<Imagen> img,
	     String audioUrl,
	     Long albumId){
    
}
