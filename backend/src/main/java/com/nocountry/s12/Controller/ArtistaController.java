package com.nocountry.s12.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nocountry.s12.Dto.Request.ModificaArtistaDTO;
import com.nocountry.s12.Dto.Response.ArtistaDTO;
import com.nocountry.s12.Service.ArtistaService;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/Artista")
@RequiredArgsConstructor
public class ArtistaController {

    private final ArtistaService artistaService;
	
    @GetMapping("/listarTodos")
    public ResponseEntity<List<ArtistaDTO>> listarTodos(){
        List<ArtistaDTO> listaArtistas = artistaService.listarTodos();
        try {
            if(listaArtistas.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(listaArtistas);
        }catch (ResponseStatusException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/listarActivos")
    public ResponseEntity<List<ArtistaDTO>> listarActivos(){
        List<ArtistaDTO> listaArtistas = artistaService.listarActivos();
        try {
            if(listaArtistas.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(listaArtistas);
        }catch (ResponseStatusException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
	

    @GetMapping("/{id}")
    public ResponseEntity<?> verArtista(@PathVariable("id") Long id){
        try {
                ArtistaDTO artistaDTO = artistaService.verArtista(id);

            return new ResponseEntity<>(artistaDTO, HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    
    @GetMapping("/buscarNombre")
    public ResponseEntity<?> buscarNombre(@RequestParam String nombre) {
        List<ArtistaDTO> listaArtistas = artistaService.buscarPorNombre(nombre);
        try {
            if(nombre == null || nombre.length() < 3) {
                return ResponseEntity.status(HttpStatus.OK).body("Debe ingresar al menos 3 caracteres");
            }

            if(listaArtistas.size() == 0) {
                return ResponseEntity.status(HttpStatus.OK).body("No hay proveedores con ese nombre");
            }

            return ResponseEntity.ok(listaArtistas);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }  
    
    @GetMapping("/buscarCampo")
    public ResponseEntity<?> buscarCampo(@RequestParam String campoArtistico) {
        List<ArtistaDTO> listaArtistas = artistaService.buscarPorCampo(campoArtistico);
        try {
            if(listaArtistas.size() == 0) {
                return ResponseEntity.status(HttpStatus.OK).body("No hay artistas de ese campo Artístico");
            }

            return ResponseEntity.ok(listaArtistas);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/buscarGenero")
    public ResponseEntity<?> buscarGenero(@RequestParam String genero) {
        List<ArtistaDTO> listaArtistas = artistaService.buscarPorGenero(genero);
        try {
            if(listaArtistas.size() == 0) {
                return ResponseEntity.status(HttpStatus.OK).body("No hay artistas de ese género");
            }

            return ResponseEntity.ok(listaArtistas);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
	
    @PutMapping("/{id}")
    public ResponseEntity<?> modificarArtista(@PathVariable("id") Long id, 
    		@RequestBody ModificaArtistaDTO modificaArtistaDTO){
    	try {
        	ArtistaDTO artistaModificado = artistaService.modificarArtista(id, modificaArtistaDTO);    	
        	return new ResponseEntity<>(artistaModificado, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    	}
    	
    }
	

    @PatchMapping("/baja")
    public ResponseEntity<?> bajaArtista(@RequestParam Long id){
        try {
        	artistaService.bajaArtista(id);
            return ResponseEntity.ok("Artista dado de baja con éxito");
        } catch (RuntimeException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
