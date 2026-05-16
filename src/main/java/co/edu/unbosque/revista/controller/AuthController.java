package co.edu.unbosque.revista.controller;

import co.edu.unbosque.revista.dto.UsuarioDTO;
import co.edu.unbosque.revista.entity.Usuario;
import co.edu.unbosque.revista.security.JwtUtil;
import co.edu.unbosque.revista.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/revista/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Autenticación", description = "API para el ingreso y registro a la revista")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final UsuarioService userService;

	public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioService userService) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.userService = userService;
	}

	@Operation(summary = "Iniciar sesión", description = "Envía nombre y contraseña para recibir un Token JWT.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Exitoso", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
			@ApiResponse(responseCode = "401", description = "Credenciales incorrectas") })
	@PostMapping("/login")
	public ResponseEntity<?> login(
			@Parameter(description = "Credenciales del usuario", required = true, examples = @ExampleObject(value = "{\"nombre\": \"Administrador Principal\", \"contrasenia\": \"User2026*/s\"}")) @RequestBody UsuarioDTO loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getNombre(), loginRequest.getContrasenia()));

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String jwt = jwtUtil.generateToken(userDetails);

			String role = null;
			if (userDetails instanceof Usuario) {
				Usuario user = (Usuario) userDetails;
				role = user.getRol().name();
			}

			return ResponseEntity.ok(new AuthResponse(jwt, role));

		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Error: Correo o contraseña incorrectos.");
		}
	}

	@Operation(summary = "Registrar nuevo usuario", description = "Permite a un usuario crearse una cuenta (Rol USUARIO por defecto).")
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UsuarioDTO registerRequest) {
		if (userService.findUsernameAlreadyTaken(registerRequest.getNombre())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Ese nombre ya está en uso");
		}

		int result = userService.create(registerRequest);
		if (result == 0) {
			return ResponseEntity.status(HttpStatus.CREATED).body("¡Bienvenido! Usuario registrado.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo completar el registro.");
		}
	}

	public static class AuthResponse {
		private String token;
		private String role;

		public AuthResponse(String token, String role) {
			this.token = token;
			this.role = role;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}
	}
}