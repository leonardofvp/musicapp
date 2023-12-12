/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nocountry.s12.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nocountry.s12.models.Evento;


/**
 *
 * @author Admin
 */

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long>{

    Optional<Evento> findByTitulo(String titulo);
    
}
