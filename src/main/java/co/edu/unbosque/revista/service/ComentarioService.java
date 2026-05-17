package co.edu.unbosque.revista.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.revista.dto.ComentarioDTO;
import co.edu.unbosque.revista.entity.Comentario;
import co.edu.unbosque.revista.repository.ComentarioRepository;

@Service
public class ComentarioService {

	@Autowired
	private ComentarioRepository comentarioRep;

	@Autowired
	private ModelMapper mapper;

	public int create(ComentarioDTO data) {
		if (data.getTexto() == null || data.getTexto().isBlank()) {
			return 1;
		}
		if (data.getPublicacionId() == null || data.getEditorId() == null) {
			return 1;
		}

		Comentario entity = mapper.map(data, Comentario.class);
		entity.setFechaComentario(LocalDateTime.now());
		comentarioRep.save(entity);
		return 0;
	}

	public List<ComentarioDTO> getAll() {
		Iterable<Comentario> lista = comentarioRep.findAll();
		List<ComentarioDTO> dtoList = new ArrayList<>();
		lista.forEach(c -> dtoList.add(mapper.map(c, ComentarioDTO.class)));
		return dtoList;
	}

	public int updateById(Long id, ComentarioDTO newData) {
		Optional<Comentario> existente = comentarioRep.findById(id);
		if (existente.isPresent()) {
			Comentario c = existente.get();
			c.setTexto(newData.getTexto());
			comentarioRep.save(c);
			return 0;
		}
		return 1;
	}

	public int deleteById(Long id) {
		if (comentarioRep.existsById(id)) {
			comentarioRep.deleteById(id);
			return 0;
		}
		return 1;
	}

	public List<ComentarioDTO> findByPublicacionId(Long publicacionId) {

		Optional<List<Comentario>> comentariosOpt =
				comentarioRep.findByPublicacionId(publicacionId);

		List<ComentarioDTO> dtoList = new ArrayList<>();

		if (comentariosOpt.isPresent()) {

			List<Comentario> comentarios = comentariosOpt.get();

			comentarios.forEach(c ->
				dtoList.add(mapper.map(c, ComentarioDTO.class))
			);
		}

		return dtoList;
	}
	
}
