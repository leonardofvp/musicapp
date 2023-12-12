package com.nocountry.s12.Service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.nocountry.s12.Dto.Response.MusicResponseDto;
import com.nocountry.s12.Exception.MiException;

public interface IMusicService {

	MusicResponseDto guardarMusica(MultipartFile audio, MultipartFile img, String titulo, String genero, String fechaSubida, String albunId)throws Exception;

	ResponseEntity<Resource> obtenerCancionByName(String videoName) throws MiException;

	List<MusicResponseDto> listarAlll();

	

}
