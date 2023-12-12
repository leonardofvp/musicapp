package com.nocountry.s12.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.nocountry.s12.Repository.ImagenRepository;
import com.nocountry.s12.models.Imagen;



@Service
@Transactional
public class ImagenService {

    @Autowired
    ImagenRepository imagenRepository;
    
	@Autowired
	CloudinaryService cloudinaryService;

    public Optional<Imagen> getImagen(Long id){
        return imagenRepository.findById(id);
    }

    public Imagen save(MultipartFile multipartFile) throws IOException{
    	
		Map <?,?>result = cloudinaryService.upload(multipartFile);
		Imagen imagen = new Imagen();
		imagen.setName((String) result.get("original_filename"));
		imagen.setImagenUrl((String) result.get("url"));
		imagen.setCloudinaryId((String) result.get("public_id"));
		
        return imagenRepository.save(imagen); 
        
    }

    public void delete(Long id){
        imagenRepository.deleteById(id);
    }

    public boolean exists(Long id){
        return imagenRepository.existsById(id);
    }
    
    public List<Imagen> list(){
        return imagenRepository.findByOrderById();
    }
}