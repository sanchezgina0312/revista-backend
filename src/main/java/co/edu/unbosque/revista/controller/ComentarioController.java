package co.edu.unbosque.revista.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.revista.dto.ComentarioDTO;
import co.edu.unbosque.revista.service.ComentarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/revista/comentarios")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8081"})
@Tag(name = "Comentario", description = "Controlador para la gestión de comentarios en las publicaciones")
public class ComentarioController {

	@Autowired
	private ComentarioService comentarioSer;

	@Operation(summary = "Añadir un comentario")
	@PostMapping(value = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> crear(@RequestBody ComentarioDTO data) {
		if (comentarioSer.create(data) == 0) {
			return new ResponseEntity<>("Comentario enviado", HttpStatus.CREATED);
		}
		return new ResponseEntity<>("Error al procesar el comentario", HttpStatus.NOT_ACCEPTABLE);
	}

	@Operation(summary = "Ver todos los comentarios (Admin)")
	@GetMapping("/listar")
	public ResponseEntity<List<ComentarioDTO>> listarTodo() {
		List<ComentarioDTO> lista = comentarioSer.getAll();
		return (lista.isEmpty()) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@Operation(summary = "Editar texto de un comentario")
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody ComentarioDTO data) {
		return (comentarioSer.updateById(id, data) == 0) ? 
				new ResponseEntity<>("Comentario editado", HttpStatus.ACCEPTED) : 
				new ResponseEntity<>("No se pudo editar", HttpStatus.NOT_FOUND);
	}

	@Operation(summary = "Borrar un comentario")
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		return (comentarioSer.deleteById(id) == 0) ? 
				new ResponseEntity<>("Comentario borrado", HttpStatus.ACCEPTED) : 
				new ResponseEntity<>("Error al borrar", HttpStatus.NOT_FOUND);
	}

	@Operation(summary = "Listar comentarios de una publicación específica")
	@GetMapping("/buscar/publicacion/{pubId}")
	public ResponseEntity<List<ComentarioDTO>> buscarPorPublicacion(@PathVariable Long pubId) {
		List<ComentarioDTO> lista = comentarioSer.findByPublicacionId(pubId);
		return (lista.isEmpty()) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(lista, HttpStatus.OK);
	}
}