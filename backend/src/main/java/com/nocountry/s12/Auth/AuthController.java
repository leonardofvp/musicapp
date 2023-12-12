package com.nocountry.s12.Auth;

import com.nocountry.s12.Dto.Request.LoginDto;
import com.nocountry.s12.Dto.Request.RegistroDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody LoginDto datos) {
		try {
			return ResponseEntity.ok(authService.login(datos));
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("registro")
	public ResponseEntity<?> registro(@RequestBody @Valid RegistroDto datos) {
		try {
			return ResponseEntity.ok(authService.registro(datos));
		} catch (RuntimeException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
