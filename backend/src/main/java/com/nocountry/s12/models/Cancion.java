/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nocountry.s12.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Admin
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="canciones")
public class Cancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String genero;
    private LocalDate fechaSubida;
    private String urlCancion;
    
    @ManyToOne
    @JoinColumn(name="id_album")
    @JsonIgnore
    private Album album;
    
    @OneToMany
    private List<Imagen> imgMusic;
    
    
    public Cancion(String titulo, String genero, String fechaSubida,  Album album, Imagen img, String urlCancion) {
    	this.titulo =titulo;
    	this.genero =genero;
    	this.fechaSubida = LocalDate.parse(fechaSubida); 
    	this.album=album;
    	this.imgMusic= new ArrayList<>();
    	this.imgMusic.add(img);
    	this.urlCancion=urlCancion;
    
    }
}
