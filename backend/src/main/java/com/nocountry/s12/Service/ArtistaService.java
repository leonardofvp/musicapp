package com.nocountry.s12.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nocountry.s12.Dto.Request.ModificaArtistaDTO;
import com.nocountry.s12.Dto.Response.ArtistaDTO;
import com.nocountry.s12.Enum.Roles;
import com.nocountry.s12.Exception.UserNotExistException;
import com.nocountry.s12.Repository.ArtistaRepository;
import com.nocountry.s12.mapper.ArtistaMapper;
import com.nocountry.s12.models.Artista;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArtistaService {

	private final ArtistaRepository artistaRepository;
    private final ArtistaMapper artistaMapper;

	public List<ArtistaDTO> listarTodos() {
		List<Artista> listaArtistas = artistaRepository.findAll();
		List<ArtistaDTO> listaArtistasDto = artistaMapper.toDtoList(listaArtistas);
		return listaArtistasDto;
	}
	
	public List<ArtistaDTO> listarActivos() {
		List<Artista> listaArtistas = artistaRepository.findByAltaTrue();
		List<ArtistaDTO> listaArtistasDto = artistaMapper.toDtoList(listaArtistas);
		return listaArtistasDto;
	}	
	

	public ArtistaDTO verArtista(Long id) {
		Artista artista = artistaRepository.findById(id).get();
        ArtistaDTO artistaDto = artistaMapper.ArtistaToArtistaDTO(artista);
		return artistaDto;
	}
	
    public List<ArtistaDTO> buscarPorNombre(String nombre) {
        List<Artista> listaArtistas = artistaRepository.buscarPorNombre(nombre);
		List<ArtistaDTO> listaArtistasDto = artistaMapper.toDtoList(listaArtistas);
		return listaArtistasDto;
    }
    
    public List<ArtistaDTO> buscarPorCampo(String campo) {
        List<Artista> listaArtistas = artistaRepository.findByCampoArtistico(campo);
		List<ArtistaDTO> listaArtistasDto = artistaMapper.toDtoList(listaArtistas);
		return listaArtistasDto;
    }
    
    public List<ArtistaDTO> buscarPorGenero(String genero) {
        List<Artista> listaArtistas = artistaRepository.findByGeneroMusical(genero);
		List<ArtistaDTO> listaArtistasDto = artistaMapper.toDtoList(listaArtistas);
		return listaArtistasDto;
    }
    
	public ArtistaDTO modificarArtista(Long id, ModificaArtistaDTO modificaArtistaDTO) {
		Artista artistaModificado = artistaRepository.findById(id).get();	
		
		artistaModificado.setNombreCompleto(modificaArtistaDTO.getNombre());
		artistaModificado.setApellidoCompleto(modificaArtistaDTO.getApellido());
		artistaModificado.setUsername(modificaArtistaDTO.getEmail());
		artistaModificado.setRol(Roles.valueOf(modificaArtistaDTO.getRol()));
		artistaModificado.setNombreArtistico(modificaArtistaDTO.getNombreArtistico());
		artistaModificado.setDescripcion(modificaArtistaDTO.getDescripcion());
		artistaModificado.setCampoArtistico(modificaArtistaDTO.getCampoArtistico());
		artistaModificado.setGeneroMusical(modificaArtistaDTO.getGeneroMusical());
		
		Artista artistaGuardado = artistaRepository.save(artistaModificado);
        ArtistaDTO artistaModificadoDTO = artistaMapper.ArtistaToArtistaDTO(artistaGuardado);
		
	    return artistaModificadoDTO;	
	}

	public void bajaArtista(Long id) {
		Optional<Artista> artistaOptional = artistaRepository.findById(id);

		if (!artistaOptional.isPresent()) {
			throw new UserNotExistException();
		}
		Artista artista = artistaOptional.get();
		artista.setAlta(false);
		artistaRepository.save(artista);
	}

        public Artista getByUsername(String username){
		Optional<Artista> artistaOp = artistaRepository.findByUsername(username);
                Artista artista = artistaOp.get();
                return artista;
	}
}
