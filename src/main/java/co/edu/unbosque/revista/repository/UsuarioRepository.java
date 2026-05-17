package co.edu.unbosque.revista.repository;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

import co.edu.unbosque.revista.entity.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	public Optional<Usuario> findByNombre(String nombre);
	public Optional<Usuario> findByCorreo(String correo);
	public Optional<Usuario> findByRol(String rol);
	public void deleteByNombre(String nombre);
}
