package com.nocountry.s12.ServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nocountry.s12.Dto.Request.EventoRequestDTO;
import com.nocountry.s12.Dto.Response.EventoResponseDTO;
import com.nocountry.s12.Exception.MiException;
import com.nocountry.s12.Repository.EventoRepository;
import com.nocountry.s12.Service.EventoService;
import com.nocountry.s12.models.Evento;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventoServiceImpl implements EventoService{

    
    private final EventoRepository eventoRepository;
   

    // public EventoServiceImpl(EventoRepository eventoRepository, ArtistaRepository artistaRepository) {
    //     this.eventoRepository = eventoRepository;
    //     this.artistaRepository = artistaRepository;
    // }

    @Override
    public List<EventoResponseDTO> findAll() throws Exception {
        try {
            
            List<Evento> eventos = eventoRepository.findAll();
            List<EventoResponseDTO> listaEventoDto = new ArrayList<EventoResponseDTO>();
            for(Evento eventosLista : eventos){
                EventoResponseDTO dto = new EventoResponseDTO(eventosLista);
                listaEventoDto.add(dto);
            }
            return listaEventoDto;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public EventoResponseDTO findById(Long id) throws Exception {
        try {
            Evento evento = eventoRepository.findById(id).get();
            EventoResponseDTO eventoDto = new EventoResponseDTO(evento);
            return eventoDto;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public boolean existsById (Long id) {
		return eventoRepository.existsById(id);
	}

    @Override
    @Transactional
    public EventoResponseDTO save(EventoRequestDTO eventoDto) throws Exception {
        Evento nuevoEvento = new Evento();
        
        // Buscar usuario loguado por token
        // String userName = jwtService.getUsernameFromToken(null)
        // String username = getArtistaFromToken();
        // System.out.println("username loged:" + username);
        // Artista artista = new Artista();
        // artista = artistaRepository.findByUsername(username).get();
        // System.out.println("artista id: " + artista.getId());

        try{
            LocalDate fechaEvento = validarFecha(eventoDto.fechaEvento());
            nuevoEvento.setFechaEvento(fechaEvento);
            nuevoEvento.setHora(eventoDto.hora());
            nuevoEvento.setLugar(eventoDto.lugar());
            nuevoEvento.setPrecio(eventoDto.precio());
            nuevoEvento.setTitulo(eventoDto.titulo());
            nuevoEvento.setDescripcion(eventoDto.descripcion());
            //nuevoEvento.setArtista(artista);
            
             eventoRepository.save(nuevoEvento);
            
            EventoResponseDTO eventoCreado = new EventoResponseDTO(nuevoEvento);
            nuevoEvento.setId(eventoCreado.idEvento());
            return eventoCreado;

        }catch (Exception e) {
            System.out.println("Service error:" +e.getMessage());
            throw new Exception(e.getMessage());
        }
           // return null;
        
    }

    @Override
    @Transactional
    public EventoResponseDTO update(Long id, EventoRequestDTO evento) throws Exception {
        Evento eventoToUpdate = new Evento();

        try {
            if (eventoRepository.existsById(id));
            LocalDate fechaEvento = validarFecha(evento.fechaEvento());
            eventoToUpdate.setFechaEvento(fechaEvento);
            eventoToUpdate.setHora(evento.hora());
            eventoToUpdate.setLugar(evento.lugar());
            eventoToUpdate.setPrecio(evento.precio());
            eventoToUpdate.setTitulo(evento.titulo());
            eventoToUpdate.setDescripcion(evento.descripcion());
            eventoToUpdate.setId(id);
            Evento eventoUpdated = eventoRepository.save(eventoToUpdate);
            EventoResponseDTO eventoResponseDTO = new EventoResponseDTO(eventoUpdated);
            return eventoResponseDTO;
        } catch (Exception e) {
              throw new Exception(e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public boolean delete(Long id) throws Exception {
        try {
            if(eventoRepository.existsById(id)){
                eventoRepository.deleteById(id);
                return true;
            }else{
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
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

    public String getArtistaFromToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
        String currentUserName = authentication.getName();
        return currentUserName;
    }
        return null;
    }

}
