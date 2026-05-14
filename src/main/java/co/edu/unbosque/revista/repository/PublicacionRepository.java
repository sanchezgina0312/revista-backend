package co.edu.unbosque.revista.repository;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.revista.entity.Publicacion;

import java.util.List;
import java.util.Optional;

public interface PublicacionRepository extends CrudRepository<Publicacion, Long> {
	
	public Optional<List<Publicacion>> findByTipo(String tipo);
    public Optional<List<Publicacion>> findByEditorId(Long editorId);
    public Optional<List<Publicacion>> findByTitulo(String palabraClave);
}
