/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nocountry.s12.Service;

import com.nocountry.s12.Dto.Request.AlbumRequestDTO;
import com.nocountry.s12.Dto.Response.AlbumResponseDTO;
import com.nocountry.s12.Exception.MiException;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface AlbumService {
    List<AlbumResponseDTO> listar();
    AlbumResponseDTO obtenerPorID(Long id);
    AlbumResponseDTO crear(AlbumRequestDTO album, String usernameArtista) throws MiException;
    AlbumResponseDTO modificar(Long id, AlbumRequestDTO album) throws MiException;
    void eliminar(Long id);
    AlbumResponseDTO baja(Long id);
}
