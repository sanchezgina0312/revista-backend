package co.edu.unbosque.revista.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.revista.dto.PublicacionDTO;
import co.edu.unbosque.revista.service.PublicacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/revista/publicaciones")
@CrossOrigin(origins = { "*"})
@Tag(name = "Publicación", description = "Gestión de noticias y horóscopos de la revista")
public class PublicacionController {

	@Autowired
	private PublicacionService publicacionSer;

	@Operation(summary = "Crear nueva publicación", description = "Guarda una noticia o un horóscopo. La fecha se asigna automáticamente.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Creado"),
		@ApiResponse(responseCode = "406", description = "Error en los datos")
	})
	@PostMapping(value = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> crear(@RequestBody PublicacionDTO data) {
		if (publicacionSer.create(data) == 0) {
			return new ResponseEntity<>("Publicación creada con éxito", HttpStatus.CREATED);
		}
		return new ResponseEntity<>("Error: Faltan datos obligatorios", HttpStatus.NOT_ACCEPTABLE);
	}

	@Operation(summary = "Listar todas las publicaciones")
	@GetMapping("/listar")
	public ResponseEntity<List<PublicacionDTO>> listarTodo() {
		List<PublicacionDTO> lista = publicacionSer.getAll();
		return (lista.isEmpty()) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@Operation(summary = "Actualizar publicación por ID")
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody PublicacionDTO data) {
		return (publicacionSer.updateById(id, data) == 0) ? 
				new ResponseEntity<>("Actualizado correctamente", HttpStatus.ACCEPTED) : 
				new ResponseEntity<>("Publicación no encontrada", HttpStatus.NOT_FOUND);
	}

	@Operation(summary = "Eliminar publicación por ID")
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		return (publicacionSer.deleteById(id) == 0) ? 
				new ResponseEntity<>("Eliminado", HttpStatus.ACCEPTED) : 
				new ResponseEntity<>("ID inexistente", HttpStatus.NOT_FOUND);
	}

	@Operation(summary = "Buscar por tipo", description = "Filtrar por 'NOTICIA' u 'HOROSCOPO'")
	@GetMapping("/buscar/tipo")
	public ResponseEntity<List<PublicacionDTO>> buscarPorTipo(@RequestParam String tipo) {
		List<PublicacionDTO> lista = publicacionSer.findByTipo(tipo);
		return (lista.isEmpty()) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@Operation(summary = "Buscar por palabra clave en el título")
	@GetMapping("/buscar/titulo")
	public ResponseEntity<List<PublicacionDTO>> buscarPorTitulo(@RequestParam String palabra) {
		List<PublicacionDTO> lista = publicacionSer.findByTitulo(palabra);
		return (lista.isEmpty()) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@Operation(summary = "Listar publicaciones de un editor específico")
	@GetMapping("/buscar/editor/{editorId}")
	public ResponseEntity<List<PublicacionDTO>> buscarPorEditor(@PathVariable Long editorId) {
		List<PublicacionDTO> lista = publicacionSer.findByEditorId(editorId);
		return (lista.isEmpty()) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@Operation(summary = "Encriptar texto plano a Base64")
	@PostMapping("/encriptar-texto")
	public ResponseEntity<String> encriptarTexto(@RequestBody String texto) {
		String cifrado = java.util.Base64.getEncoder().encodeToString(texto.getBytes(java.nio.charset.StandardCharsets.UTF_8));
		return new ResponseEntity<>(cifrado, HttpStatus.OK);
	}

	@Operation(summary = "Desencriptar Base64 a texto plano legible")
	@PostMapping("/desencriptar-texto")
	public ResponseEntity<String> desencriptarTexto(@RequestBody String textoCifrado) {
		try {
			byte[] decodedBytes = java.util.Base64.getDecoder().decode(textoCifrado.trim());
			String descifrado = new String(decodedBytes, java.nio.charset.StandardCharsets.UTF_8);
			return new ResponseEntity<>(descifrado, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(textoCifrado, HttpStatus.OK);
		}
	}
}