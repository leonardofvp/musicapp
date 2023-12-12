/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nocountry.s12.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="artista")
public class Artista extends Usuario{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombreArtistico;
    private String descripcion;
	
    @OneToMany(mappedBy = "artista")
    private List<Album> albums;
    
    @OneToMany(mappedBy = "artista")
    private List<Evento> eventos;
    
    private String campoArtistico;
    private String generoMusical;

}
