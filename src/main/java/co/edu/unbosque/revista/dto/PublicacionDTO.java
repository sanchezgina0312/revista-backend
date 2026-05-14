package co.edu.unbosque.revista.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class PublicacionDTO {

	private Long id;
	private String titulo;
	private String contenido;
	private String tipo;
	private LocalDateTime fechaCreacion;
	private Long editorId;

	public PublicacionDTO() {

	}

	public PublicacionDTO(String titulo, String contenido, String tipo, LocalDateTime fechaCreacion, Long editorId) {
		super();
		this.titulo = titulo;
		this.contenido = contenido;
		this.tipo = tipo;
		this.fechaCreacion = fechaCreacion;
		this.editorId = editorId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Long getEditorId() {
		return editorId;
	}

	public void setEditorId(Long editorId) {
		this.editorId = editorId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contenido, editorId, fechaCreacion, id, tipo, titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PublicacionDTO other = (PublicacionDTO) obj;
		return Objects.equals(contenido, other.contenido) && Objects.equals(editorId, other.editorId)
				&& Objects.equals(fechaCreacion, other.fechaCreacion) && Objects.equals(id, other.id)
				&& Objects.equals(tipo, other.tipo) && Objects.equals(titulo, other.titulo);
	}

	@Override
	public String toString() {
		return "PublicacionDTO [id=" + id + ", titulo=" + titulo + ", contenido=" + contenido + ", tipo=" + tipo
				+ ", fechaCreacion=" + fechaCreacion + ", editorId=" + editorId + "]";
	}

}
