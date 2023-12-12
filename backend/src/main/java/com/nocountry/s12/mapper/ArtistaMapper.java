package com.nocountry.s12.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nocountry.s12.Dto.Response.ArtistaDTO;
import com.nocountry.s12.models.Artista;

@Mapper(componentModel = "spring")
public interface ArtistaMapper {
	
	
	@Mapping(target = "fotoPerfil", source= "fotoPerfil.imagenUrl")
	@Mapping(target = "fotoPortada", source= "fotoPortada.imagenUrl")
	ArtistaDTO ArtistaToArtistaDTO(Artista artista);
	
	@Mapping(target = "fotoPerfil", source= "fotoPerfil.imagenUrl")
	@Mapping(target = "fotoPortada", source= "fotoPortada.imagenUrl")
	List<ArtistaDTO> toDtoList(List<Artista> listaArtistas);
}
