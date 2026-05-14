package co.edu.unbosque.revista.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

	public int create(UsuarioDTO data) {
		if (data.getNombre() == null || data.getNombre().isBlank())
			return 1;
		if (data.getCorreo() == null || data.getCorreo().isBlank())
			return 1;
		if (data.getContrasenia() == null || data.getContrasenia().isBlank())
			return 1;
		if (data.getRol() == null || data.getRol().isBlank())
			return 1;

		Usuario entity = mapper.map(data, Usuario.class);
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
}
