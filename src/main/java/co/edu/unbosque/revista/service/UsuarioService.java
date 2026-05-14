package co.edu.unbosque.revista.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.unbosque.revista.dto.UsuarioDTO;
import co.edu.unbosque.revista.entity.Usuario;
import co.edu.unbosque.revista.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRep;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UsuarioService() {
	}

	public long count() {
		return usuarioRep.count();
	}

	public boolean exist(Long id) {
		return usuarioRep.existsById(id);
	}

	public int create(UsuarioDTO data) {
		if (data.getNombre() == null || data.getNombre().isBlank()) {
			return 1;
		}
		if (data.getCorreo() == null || data.getCorreo().isBlank()) {
			return 1;
		}
		if (data.getContrasenia() == null || data.getContrasenia().isBlank()) {
			return 1;
		}
		if (data.getRol() == null || data.getRol().isBlank()) {
			return 1;
		}

		Usuario entity = mapper.map(data, Usuario.class);

		entity.setContrasenia(passwordEncoder.encode(data.getContrasenia()));

		usuarioRep.save(entity);
		return 0;
	}

	public List<UsuarioDTO> getAll() {
		Iterable<Usuario> lista = usuarioRep.findAll();
		List<UsuarioDTO> dtoList = new ArrayList<>();
		lista.forEach(u -> dtoList.add(mapper.map(u, UsuarioDTO.class)));
		return dtoList;
	}

	public int updateById(Long id, UsuarioDTO newData) {
		Optional<Usuario> existente = usuarioRep.findById(id);
		if (existente.isPresent()) {
			Usuario u = existente.get();
			u.setNombre(newData.getNombre());
			u.setCorreo(newData.getCorreo());
			u.setRol(newData.getRol());

			if (newData.getContrasenia() != null && !newData.getContrasenia().isBlank()) {
				u.setContrasenia(passwordEncoder.encode(newData.getContrasenia()));
			}

			usuarioRep.save(u);
			return 0;
		}
		return 1;
	}

	public int deleteById(Long id) {
		if (usuarioRep.existsById(id)) {
			usuarioRep.deleteById(id);
			return 0;
		}
		return 1;
	}

	public List<UsuarioDTO> findByNombre(String nombre) {
		Optional<List<Usuario>> encontrados = usuarioRep.findByNombre(nombre);
		List<UsuarioDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach(entity -> dtoList.add(mapper.map(entity, UsuarioDTO.class)));
		}
		return dtoList;
	}

	public List<UsuarioDTO> findByCorreo(String correo) {
		Optional<List<Usuario>> encontrados = usuarioRep.findByCorreo(correo);
		List<UsuarioDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach(entity -> dtoList.add(mapper.map(entity, UsuarioDTO.class)));
		}
		return dtoList;
	}

	public List<UsuarioDTO> findByRol(String rol) {
		Optional<List<Usuario>> encontrados = usuarioRep.findByRol(rol);
		List<UsuarioDTO> dtoList = new ArrayList<>();
		if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
			encontrados.get().forEach(entity -> dtoList.add(mapper.map(entity, UsuarioDTO.class)));
		}
		return dtoList;
	}

	public int deleteByUsername(String username) {
		Optional<List<Usuario>> foundList = usuarioRep.findByNombre(username);
		if (foundList.isPresent() && !foundList.get().isEmpty()) {
			usuarioRep.delete(foundList.get().get(0));
			return 0;
		} else {
			return 1;
		}
	}

	public UsuarioDTO getById(Long id) {
		Optional<Usuario> found = usuarioRep.findById(id);
		if (found.isPresent()) {
			return mapper.map(found.get(), UsuarioDTO.class);
		} else {
			return null;
		}
	}

	public boolean findUsernameAlreadyTaken(Usuario newUser) {
		Optional<List<Usuario>> foundList = usuarioRep.findByNombre(newUser.getNombre());
		if (foundList.isPresent() && !foundList.get().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Verifica si un nombre de usuario ya está en uso.
	 *
	 * @param username Nombre de usuario a verificar
	 * @return true si el nombre de usuario ya está en uso, false en caso contrario
	 */
	public boolean findUsernameAlreadyTaken(String username) {
		Optional<List<Usuario>> foundList = usuarioRep.findByNombre(username);
		return foundList.isPresent() && !foundList.get().isEmpty();
	}

	/**
	 * Valida las credenciales de un usuario.
	 *
	 * @param username Nombre de usuario
	 * @param password Contraseña sin encriptar
	 * @return 0 si las credenciales son válidas, 1 si son inválidas
	 */
	public int validateCredentials(String username, String password) {
		Optional<List<Usuario>> userOptList = usuarioRep.findByNombre(username);

		if (userOptList.isPresent() && !userOptList.get().isEmpty()) {
			Usuario user = userOptList.get().get(0);
			if (passwordEncoder.matches(password, user.getContrasenia())) {
				return 0;
			}
		}

		return 1;
	}
}
