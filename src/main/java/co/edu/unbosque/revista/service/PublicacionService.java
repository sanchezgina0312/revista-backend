package co.edu.unbosque.revista.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.revista.util.EncriptadorUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import co.edu.unbosque.revista.dto.PublicacionDTO;
import co.edu.unbosque.revista.entity.Publicacion;
import co.edu.unbosque.revista.repository.PublicacionRepository;

@Service
public class PublicacionService {

	@Autowired
	private EncriptadorUtil encriptadorUtil;

	@Autowired
	private PublicacionRepository publicacionRep;

	@Autowired
	private ModelMapper mapper;

	public String obtenerTextoProtegido(String textoReal) {
		return encriptadorUtil.encriptarTexto(textoReal);
	}

	public String obtenerTextoLimpio(String textoFalso) {
		return encriptadorUtil.desencriptarTexto(textoFalso);
	}

	public int create(PublicacionDTO data) {
		if (data.getTitulo() == null || data.getTitulo().isBlank())
			return 1;
		if (data.getContenido() == null || data.getContenido().isBlank())
			return 1;
		if (data.getEditorId() == null)
			return 1;

		Publicacion entity = mapper.map(data, Publicacion.class);
		entity.setFechaCreacion(LocalDateTime.now());
		publicacionRep.save(entity);
		return 0;
	}

	public List<PublicacionDTO> getAll() {
		Iterable<Publicacion> lista = publicacionRep.findAll();
		List<PublicacionDTO> dtoList = new ArrayList<>();
		lista.forEach(p -> dtoList.add(mapper.map(p, PublicacionDTO.class)));
		return dtoList;
	}

	public int updateById(Long id, PublicacionDTO newData) {
		Optional<Publicacion> existente = publicacionRep.findById(id);
		if (existente.isPresent()) {
			Publicacion p = existente.get();
			p.setTitulo(newData.getTitulo());
			p.setContenido(newData.getContenido());
			p.setTipo(newData.getTipo());
			publicacionRep.save(p);
			return 0;
		}
		return 1;
	}

	public int deleteById(Long id) {
		if (publicacionRep.existsById(id)) {
			publicacionRep.deleteById(id);
			return 0;
		}
		return 1;
	}

	public List<PublicacionDTO> findByTipo(String tipo) {
	    Optional<Publicacion> encontrada = publicacionRep.findByTipo(tipo);
	    List<PublicacionDTO> dtoList = new ArrayList<>();
	    if (encontrada.isPresent()) {
	        dtoList.add(mapper.map(encontrada.get(), PublicacionDTO.class));
	    }
	    return dtoList;
	}

	public List<PublicacionDTO> findByEditorId(Long editorId) {
	    Optional<Publicacion> encontrada = publicacionRep.findByEditorId(editorId);
	    List<PublicacionDTO> dtoList = new ArrayList<>();
	    if (encontrada.isPresent()) {
	        dtoList.add(mapper.map(encontrada.get(), PublicacionDTO.class));
	    }
	    return dtoList;
	}

	public List<PublicacionDTO> findByTitulo(String palabraClave) {
	    Optional<Publicacion> encontrada = publicacionRep.findByTitulo(palabraClave);
	    List<PublicacionDTO> dtoList = new ArrayList<>();
	    if (encontrada.isPresent()) {
	        dtoList.add(mapper.map(encontrada.get(), PublicacionDTO.class));
	    }
	    return dtoList;
	}
}
