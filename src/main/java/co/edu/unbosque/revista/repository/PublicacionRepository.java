package co.edu.unbosque.revista.repository;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.revista.entity.Publicacion;

import java.util.Optional;

public interface PublicacionRepository extends CrudRepository<Publicacion, Long> {
	
	public Optional<Publicacion> findByTipo(String tipo);
    public Optional<Publicacion> findByEditorId(Long editorId);
    public Optional<Publicacion> findByTitulo(String palabraClave);
}
