/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nocountry.s12.Service;

import java.util.List;

import com.nocountry.s12.Dto.Request.EventoRequestDTO;
import com.nocountry.s12.Dto.Response.EventoResponseDTO;


/**
 *
 * @author Admin
 */


public interface EventoService {
    
    public List<EventoResponseDTO> findAll() throws Exception;
    public EventoResponseDTO findById(Long id) throws Exception;
    public EventoResponseDTO save(EventoRequestDTO evento) throws Exception;
    public EventoResponseDTO update(Long id, EventoRequestDTO evento) throws Exception;
    public boolean delete(Long id) throws Exception;

}
