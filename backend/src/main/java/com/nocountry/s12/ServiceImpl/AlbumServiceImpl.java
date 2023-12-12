/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nocountry.s12.ServiceImpl;

import com.nocountry.s12.Dto.Request.AlbumRequestDTO;
import com.nocountry.s12.Dto.Response.AlbumResponseDTO;
import com.nocountry.s12.Exception.MiException;
import com.nocountry.s12.Repository.AlbumRepository;
import com.nocountry.s12.Service.AlbumService;
import com.nocountry.s12.Service.ArtistaService;
import com.nocountry.s12.models.Album;
import com.nocountry.s12.models.Artista;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistaService artistaService;

    @Override
    public List<AlbumResponseDTO> listar() {
        List<Album> listaAlbum = albumRepository.findAll();
        List<AlbumResponseDTO> listaAlbumDTO = listaAlbum.stream()
                .map(AlbumResponseDTO::new).collect(Collectors.toList());
        return listaAlbumDTO;
    }

    @Override
    public AlbumResponseDTO obtenerPorID(Long id) {
        Album album = albumRepository.findById(id).get();
        AlbumResponseDTO albumResponseDTO = new AlbumResponseDTO(album);
        return albumResponseDTO;
    }

    @Override
    public AlbumResponseDTO crear(AlbumRequestDTO albumDTO, String usernameArtista) throws MiException {
        Album nuevoAlbum = new Album();

        try {
            LocalDate fechaPublicacion = validarFecha(albumDTO.fechaPublicacion());
            Artista artista = artistaService.getByUsername(usernameArtista);

            nuevoAlbum.setGenero(albumDTO.genero());
            nuevoAlbum.setFechaPublicacion(fechaPublicacion);
            nuevoAlbum.setAlta(true);
            nuevoAlbum.setArtista(artista);

            albumRepository.save(nuevoAlbum);
        } catch (Exception e) {
            throw new MiException(e.getMessage());
        }

        AlbumResponseDTO albumResponseDTO = new AlbumResponseDTO(nuevoAlbum);
        return albumResponseDTO;
    }

    @Override
    public AlbumResponseDTO modificar(Long id, AlbumRequestDTO albumDTO) throws MiException {
        Album albumModificado = albumRepository.findById(id).get();
        LocalDate fechaPublicacion = this.validarFecha(albumDTO.fechaPublicacion());
        albumModificado.setGenero(albumDTO.genero());
        albumModificado.setFechaPublicacion(fechaPublicacion);

        /*
        List<Cancion> canciones = album.getCanciones();
        if (canciones != null && !canciones.isEmpty()) {
            for (Cancion cancion : canciones) {
                cancion.setAlbum(album);
                cancionRepository.save(cancion);
            }
        albumModificado.setCanciones(album.getCanciones());
        }*/
        albumRepository.save(albumModificado);
        AlbumResponseDTO albumResponseDTO = new AlbumResponseDTO(albumModificado);
        return albumResponseDTO;
    }

    @Override
    public void eliminar(Long id) {
        albumRepository.deleteById(id);
    }

    @Override
    public AlbumResponseDTO baja(Long id) {
        Album album = albumRepository.findById(id).get();
        album.setAlta(false);
        albumRepository.save(album);

        AlbumResponseDTO albumResponseDTO = new AlbumResponseDTO(album);
        return albumResponseDTO;
    }

    public LocalDate validarFecha(String fecha) throws MiException {
        try {
            //Si la fecha que recibe es en formato corrrecto devuelve la fecha como localdate
            LocalDate fechaConvertida = LocalDate.parse(fecha);
            return fechaConvertida;

        } catch (DateTimeParseException e) {
            throw new MiException("El formato de fecha debe ser yyyy-MM-dd", HttpStatus.BAD_REQUEST);
        }
    }

}
