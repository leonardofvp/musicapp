package com.nocountry.s12.Dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModificaArtistaDTO {

    String nombre;
    String apellido; 
    
	@NotBlank (message = "Campo obligatorio.")
    @Email(message = "Debe ingresar una dirección de correo válida.")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?{|}~^.-]+@[a-zA-Z0-9.-]+$",
            message = "Debe ingresar una dirección de correo válida.")
    String email;
    String rol;        
    @NotBlank (message = "Campo obligatorio.")
    @Size(min = 2, max = 30, message = "El nombre artístico debe contener entre 2 y 30 caracteres.")
    String nombreArtistico;
    @NotBlank (message = "Campo obligatorio.")
    @Size(min = 10, max = 100, message = "La descripción debe contener entre 10 y 100 caracteres.")
    String descripcion; 
   
    String campoArtistico;
    String generoMusical;

    
    
    
}
