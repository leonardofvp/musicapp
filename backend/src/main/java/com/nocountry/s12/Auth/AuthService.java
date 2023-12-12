package com.nocountry.s12.Auth;

import java.util.Optional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nocountry.s12.Dto.Request.LoginDto;
import com.nocountry.s12.Dto.Request.RegistroDto;
import com.nocountry.s12.Dto.Response.AuthResponse;
import com.nocountry.s12.Enum.Roles;
import com.nocountry.s12.Jwt.JwtService;
import com.nocountry.s12.Repository.ArtistaRepository;
import com.nocountry.s12.Repository.UsuarioRepository;
import com.nocountry.s12.models.Artista;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UsuarioRepository usuarioRepository;
	private final ArtistaRepository artistaRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	public AuthResponse login(LoginDto datos) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(datos.getEmail(), datos.getPassword()));
		UserDetails user = usuarioRepository.findByUsername(datos.getEmail()).orElseThrow();
		String token = jwtService.getToken(user);
		return AuthResponse.builder().token(token).build();

	}

	public AuthResponse registro(RegistroDto datos) {

		Optional<Artista> artistaOptional = artistaRepository.findByUsername(datos.getEmail());
		if (artistaOptional.isPresent()) {
			throw new RuntimeException("Ya existe un USUARIO con ese email");
		}

		Artista artista = new Artista();

		artista.setUsername(datos.getEmail());
		artista.setPassword(passwordEncoder.encode(datos.getPassword()));
		artista.setRol(Roles.valueOf(datos.getRol()));
		artista.setNombreCompleto(datos.getNombreCompleto());
		artista.setApellidoCompleto(datos.getApellidoCompleto());
		artista.setAlta(true);

		artistaRepository.save(artista);

		return AuthResponse.builder().token(jwtService.getToken(artista)).build();

	}

}