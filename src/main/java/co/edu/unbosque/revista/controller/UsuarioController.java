package co.edu.unbosque.revista.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.revista.dto.UsuarioDTO;
import co.edu.unbosque.revista.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/revista/usuarios")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8081"})
@Tag(name = "Usuario", description = "Controlador para la gestión completa de usuarios de la revista")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioSer;

	@Operation(summary = "Registrar un nuevo usuario", description = "Crea un usuario en el sistema con contraseña encriptada.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Creado"),
			@ApiResponse(responseCode = "406", description = "Datos inválidos") })
	@PostMapping(value = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> crear(@RequestBody UsuarioDTO data) {
		if (usuarioSer.create(data) == 0) {
			return new ResponseEntity<>("Usuario registrado correctamente", HttpStatus.CREATED);
		}
		return new ResponseEntity<>("Error al crear usuario", HttpStatus.NOT_ACCEPTABLE);
	}

	@Operation(summary = "Obtener todos los usuarios")
	@GetMapping("/listar")
	public ResponseEntity<List<UsuarioDTO>> listarTodo() {
		List<UsuarioDTO> lista = usuarioSer.getAll();
		return (lista.isEmpty()) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@Operation(summary = "Buscar usuario por ID")
	@GetMapping("/buscar/{id}")
	public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
		UsuarioDTO encontrado = usuarioSer.getById(id);
		return (encontrado != null) ? new ResponseEntity<>(encontrado, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@Operation(summary = "Actualizar usuario por ID")
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody UsuarioDTO data) {
		return (usuarioSer.updateById(id, data) == 0) ? new ResponseEntity<>("Actualizado", HttpStatus.ACCEPTED)
				: new ResponseEntity<>("No encontrado", HttpStatus.NOT_FOUND);
	}

	@Operation(summary = "Eliminar usuario por ID")
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminarPorId(@PathVariable Long id) {
		return (usuarioSer.deleteById(id) == 0) ? new ResponseEntity<>("Eliminado", HttpStatus.ACCEPTED)
				: new ResponseEntity<>("ID no encontrado", HttpStatus.NOT_FOUND);
	}

	@Operation(summary = "Eliminar usuario por nombre de usuario", description = "Busca y elimina permanentemente a un usuario usando su nombre.")
	@ApiResponses(value = { @ApiResponse(responseCode = "202", description = "Eliminado con éxito"),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado") })
	@DeleteMapping("/eliminarPorNombre")
	public ResponseEntity<String> eliminarPorNombre(@RequestParam String username) {
		if (usuarioSer.deleteByUsername(username) == 0) {
			return new ResponseEntity<>("Usuario eliminado exitosamente", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>("Error al eliminar: Usuario no existe", HttpStatus.NOT_FOUND);
	}

	@Operation(summary = "Verificar si el nombre de usuario ya existe")
	@GetMapping("/verificar-nombre")
	public ResponseEntity<Boolean> verificarNombre(@RequestParam String username) {
		return new ResponseEntity<>(usuarioSer.findUsernameAlreadyTaken(username), HttpStatus.OK);
	}

	@Operation(summary = "Contar total de usuarios")
	@GetMapping("/contar")
	public ResponseEntity<Long> contar() {
		return new ResponseEntity<>(usuarioSer.count(), HttpStatus.OK);
	}

	@GetMapping("/buscar/nombre")
	public ResponseEntity<List<UsuarioDTO>> buscarNombre(@RequestParam String nombre) {
		List<UsuarioDTO> lista = usuarioSer.findByNombre(nombre);
		return (lista.isEmpty()) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@GetMapping("/buscar/correo")
	public ResponseEntity<List<UsuarioDTO>> buscarCorreo(@RequestParam String correo) {
		List<UsuarioDTO> lista = usuarioSer.findByCorreo(correo);
		return (lista.isEmpty()) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@GetMapping("/buscar/rol")
	public ResponseEntity<List<UsuarioDTO>> buscarRol(@RequestParam String rol) {
		List<UsuarioDTO> lista = usuarioSer.findByRol(rol);
		return (lista.isEmpty()) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(lista, HttpStatus.OK);
	}
}