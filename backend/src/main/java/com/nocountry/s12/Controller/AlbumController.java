/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nocountry.s12.Controller;

import com.nocountry.s12.Dto.Request.AlbumRequestDTO;
import com.nocountry.s12.Dto.Response.AlbumResponseDTO;
import com.nocountry.s12.Exception.MiException;
import com.nocountry.s12.ServiceImpl.AlbumServiceImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/album")
public class AlbumController {
    
    private final AlbumServiceImpl albumService;
    
    @GetMapping("")
    public ResponseEntity<?> listar() {
        List<AlbumResponseDTO> albumResponseDTO = albumService.listar();
        return new ResponseEntity<>(albumResponseDTO, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorID(@PathVariable("id") Long id) {
        AlbumResponseDTO albumResponseDTO = albumService.obtenerPorID(id);
        return new ResponseEntity<>(albumResponseDTO, HttpStatus.OK);
    }
    
    @Secured("ARTISTA")
    @PostMapping("")
    public ResponseEntity<?> crear(@Valid @RequestBody AlbumRequestDTO albumRequestDTO, @AuthenticationPrincipal UserDetails userDetails) {
        String usernameArtista = userDetails.getUsername();
                
        try {
            albumService.crear(albumRequestDTO, usernameArtista);
            return ResponseEntity.status(HttpStatus.CREATED).body("Album creado");
        } catch (MiException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable("id") Long id, @Valid @RequestBody AlbumRequestDTO albumRequestDTO) {
        try {
            AlbumResponseDTO albumResponseDTO = albumService.modificar(id, albumRequestDTO);
            return new ResponseEntity<>(albumResponseDTO, HttpStatus.OK);
        } catch (MiException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/baja/{id}")
    public ResponseEntity<?> baja(@PathVariable("id") Long id) {
        try {
            AlbumResponseDTO albumResponseDTO = albumService.baja(id);
            return new ResponseEntity<>(albumResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") Long id) {
        albumService.eliminar(id);
    }
    

    
}
