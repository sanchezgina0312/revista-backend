package co.edu.unbosque.revista.repository;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

import co.edu.unbosque.revista.entity.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	public Optional<List<Usuario>> findByNombre(String nombre);
	public Optional<List<Usuario>> findByCorreo(String correo);
	public Optional<List<Usuario>> findByRol(String rol);
}
