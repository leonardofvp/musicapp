package com.nocountry.s12.Controller;

import com.nocountry.s12.Dto.Request.PublicacionRequestDTO;
import com.nocountry.s12.Dto.Response.PublicacionResponseDTO;
import com.nocountry.s12.Service.PublicacionService;
import com.nocountry.s12.models.Publicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publicacion")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @GetMapping
    public ResponseEntity<List<PublicacionResponseDTO>> getPublicaciones(){
        List<PublicacionResponseDTO> publicaciones = publicacionService.getPublicaciones();
        return ResponseEntity.ok(publicaciones);
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<PublicacionResponseDTO>> getPublicacionesPorUsuario(@PathVariable("id") Long id){
        List<PublicacionResponseDTO> publicaciones = publicacionService.getPublicacionesPorUsuario(id);
        return ResponseEntity.ok(publicaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionResponseDTO> getPublicacionPorId(@PathVariable("id") Long id){
        return ResponseEntity.ok(publicacionService.getPublicacionPorId(id));
    }


    @PostMapping
    public ResponseEntity<Publicacion> crearPublicacion(@RequestBody PublicacionRequestDTO publicacionRequestDTO){
        //String rutaImagen = almacenarImagen(publicacionRequestDTO.getImagen()); FALTA

        return ResponseEntity.ok(publicacionService.crearPublicacion(publicacionRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publicacion> editarPublicacion(@PathVariable("id") Long id, @RequestBody PublicacionRequestDTO publicacionRequestDTO){
        return ResponseEntity.ok(publicacionService.editarPublicacion(id, publicacionRequestDTO));
    }

    @DeleteMapping("/{id}")
    public boolean eliminarPublicacion(@PathVariable("id") Long id){
        return (publicacionService.eliminarPublicacion(id));
    }
}
