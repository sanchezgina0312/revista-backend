package co.edu.unbosque.revista.repository;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.revista.entity.Comentario;

import java.util.List;
import java.util.Optional;

public interface ComentarioRepository extends CrudRepository<Comentario, Long> {
	
	Optional<List<Comentario>> findByPublicacionId(Long publicacionId);
}
