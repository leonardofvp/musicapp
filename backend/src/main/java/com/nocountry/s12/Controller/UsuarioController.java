package com.nocountry.s12.Controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;

import com.nocountry.s12.Repository.UsuarioRepository;
import com.nocountry.s12.Service.ImagenService;
import com.nocountry.s12.models.Usuario;
import com.nocountry.s12.models.Imagen;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/Usuario")
@RequiredArgsConstructor
public class UsuarioController {
	
    private final UsuarioRepository usuarioRepository;
	private final ImagenService imagenService;
    
    @PostMapping("/fotoPerfil/{id}")
    public ResponseEntity<?> fotoPerfil(@PathVariable("id") Long id, @RequestParam("imagen") MultipartFile multipartFile)  throws IOException {
    	try {
        	Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        	
    		BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
    		if (bi == null) {
    			return new ResponseEntity<String>("imagen no válida", HttpStatus.BAD_REQUEST);
    		}
    		
    		Imagen imagen = imagenService.save(multipartFile);
        	usuario.setFotoPerfil(imagen);
        	usuarioRepository.save(usuario);
        	
    		return new ResponseEntity<Imagen>(imagen, HttpStatus.OK);   
        } catch (Exception e) {
        	return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    } 
	
	
	

}
