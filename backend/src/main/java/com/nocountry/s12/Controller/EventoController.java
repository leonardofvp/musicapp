package com.nocountry.s12.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nocountry.s12.Dto.Request.EventoRequestDTO;
import com.nocountry.s12.Dto.Response.EventoResponseDTO;
import com.nocountry.s12.ServiceImpl.EventoServiceImpl;

import io.micrometer.common.util.StringUtils;

@RestController
@RequestMapping(path = "/eventos")
public class EventoController {

	@Autowired
	private EventoServiceImpl eventoService;

	@GetMapping
	public ResponseEntity<?> getAll() {
		try {
			List<EventoResponseDTO> eventosDto = eventoService.findAll();
			return ResponseEntity.status(HttpStatus.OK).body(eventosDto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error: Por favor intente mas tarde.\"");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(eventoService.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error: Por favor intente mas tarde.\"");
		}
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody EventoRequestDTO evento) {
		// revisamos que exista el campo titulo
		if (StringUtils.isBlank(evento.titulo()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("{\"error\":\"Error: El campo nombre es obligatorio.\"");

		// revisamos que exista el campo Lugar
		if (StringUtils.isBlank(evento.lugar()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("{\"error\":\"Error: El campo lugar es obligatorio.\"");

		// revisamos que exista el campo hora
		if (StringUtils.isBlank(evento.hora()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error: La Hora es obligatoria.\"");

		// validamos que la fecha exista
		if (evento.fechaEvento().equals(null))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("{\"error\":\"Error: La fecha del evento es obligatorio.\"");

		// revisamos que exista el campo precio y sea mayor q cero
		if (evento.precio() < 0)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("{\"error\":\"Error: El precio del evento es obligatorio.\"");

		if (evento.precio() == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("{\"error\":\"Error: El precio del evento es obligatorio.\"");

		// persistimos el nuevo evento
		try {
			return ResponseEntity.status(HttpStatus.OK).body(eventoService.save(evento));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error: Por favor intente mas tarde.\"");
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody EventoRequestDTO evento) {

		// Validacion existencia de entidad a actualizar
		if (!eventoService.existsById(id))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error: No existe el evento.\"");

		// revisamos que exista el campo titulo
		if (StringUtils.isBlank(evento.titulo()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("{\"error\":\"Error: El campo nombre es obligatorio.\"");

		// revisamos que exista el campo Lugar
		if (StringUtils.isBlank(evento.lugar()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("{\"error\":\"Error: El campo lugar es obligatorio.\"");

		// revisamos que exista el campo hora
		if (StringUtils.isBlank(evento.hora()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error: La Hora es obligatoria.\"");

		// validamos que la fecha exista
		if (evento.fechaEvento().equals(null))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("{\"error\":\"Error: La fecha del evento es obligatorio.\"");

		// revisamos que exista el campo precio y sea mayor q cero
		if (evento.precio() < 0)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("{\"error\":\"Error: El precio del evento es obligatorio.\"");

		if (evento.precio() == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("{\"error\":\"Error: El precio del evento es obligatorio.\"");

		try {
			return ResponseEntity.status(HttpStatus.OK).body(eventoService.update(id, evento));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error: Por favor intente mas tarde.\"");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		// existencia de entidad a borrar
		if (!eventoService.existsById(id))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error: No existe el evento.\"");
		try {
			return ResponseEntity.status(HttpStatus.OK).body(eventoService.delete(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("{\"error\":\"Error: Por favor intente mas tarde.\"");
		}
	}

}
