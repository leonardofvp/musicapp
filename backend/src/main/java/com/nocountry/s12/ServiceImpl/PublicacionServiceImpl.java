package com.nocountry.s12.ServiceImpl;

import com.nocountry.s12.Dto.Request.PublicacionRequestDTO;
import com.nocountry.s12.Dto.Response.PublicacionResponseDTO;
import com.nocountry.s12.Exception.UserNotExistException;
import com.nocountry.s12.Repository.PublicacionRepository;
import com.nocountry.s12.Repository.UsuarioRepository;
import com.nocountry.s12.Service.PublicacionService;
import com.nocountry.s12.models.Publicacion;
import com.nocountry.s12.models.Usuario;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PublicacionServiceImpl implements PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<PublicacionResponseDTO> getPublicaciones() {

        List<Publicacion> publicaciones = publicacionRepository.findAll();
        List<PublicacionResponseDTO> listaPublicacionDto = new ArrayList<>();

        for (Publicacion publicacion : publicaciones) {
            PublicacionResponseDTO dto = new PublicacionResponseDTO(publicacion);
            listaPublicacionDto.add(dto);
        }

        return listaPublicacionDto;
    }

    @Override
    public List<PublicacionResponseDTO> getPublicacionesPorUsuario(Long idUsuario) {
        Usuario usuario = obtenerUsuarioPorId(idUsuario);
        List<Publicacion> publicaciones = usuario.getPublicaciones();

        List<PublicacionResponseDTO> listaPublicacionDto = new ArrayList<>();

        for (Publicacion publicacion : publicaciones) {
            PublicacionResponseDTO dto = new PublicacionResponseDTO(publicacion);
            listaPublicacionDto.add(dto);
        }

        return listaPublicacionDto;
    }


    @Override
    public PublicacionResponseDTO getPublicacionPorId(Long idPublicacion) {
        Publicacion publicacion = publicacionRepository.findById(idPublicacion)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la publicación con ID: " + idPublicacion));
        // excepcion personalizada?

        return new PublicacionResponseDTO(publicacion);
    }

    @Override
    public Publicacion crearPublicacion(PublicacionRequestDTO publicacionRequestDTO) {
        Optional<Usuario> usuarioAutenticado = obtenerUsuarioAutenticado();

        if (usuarioAutenticado.isPresent()) {
            Publicacion publicacion = new Publicacion();
            publicacion.setUsuario(usuarioAutenticado.get());
            publicacion.setMensaje(publicacionRequestDTO.mensaje());
            publicacion.setFechaCreacion(LocalDate.now());
            //publicacion.setImagen(publicacionRequestDTO.imagen());

            return publicacionRepository.save(publicacion);
        }
        else {
            throw new UserNotExistException();
        }
    }

    @Override
    public Publicacion editarPublicacion(Long idPublicacion, PublicacionRequestDTO publicacionRequestDTO) {


        Publicacion publicacion = publicacionRepository.findById(idPublicacion)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la publicación con ID: " + idPublicacion));
        /*
        if (!publicacion.getIdUsuario().equals(usuarioAutenticado.getId())) {
            throw new UnauthorizedAccessException();
        }

        publicacion.setMensaje(publicacionRequestDTO.getMensaje());
        publicacion.setImagen(publicacionRequestDTO.getImagen());

         */

        return publicacionRepository.save(publicacion);
    }

    @Override
    public boolean eliminarPublicacion(Long idPublicacion) {

        Publicacion publicacion = publicacionRepository.findById(idPublicacion)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la publicación con ID: " + idPublicacion));
        /*
        if (!publicacion.getUsuario().equals(usuarioAutenticado)) {
            throw new UnauthorizedAccessException();
        }
         */

        publicacionRepository.delete(publicacion);
        return true;
    }


    private Usuario obtenerUsuarioPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(UserNotExistException::new);
    }

    // TODO: Ver si funciona
    private Optional<Usuario> obtenerUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = authentication.getName();
        return usuarioRepository.findByUsername(nombreUsuario);
    }

    /*
    @Override
    public void actualizarMeGusta(Publicacion publicacion) {
        int cantidadMeGusta = publicacion.getReacciones().size();
        publicacion.setMeGusta(cantidadMeGusta);

        publicacionRepository.save(publicacion);
    }
     */
}

