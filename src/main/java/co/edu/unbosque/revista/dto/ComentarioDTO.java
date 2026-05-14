package co.edu.unbosque.revista.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class ComentarioDTO {

	private Long id;
	private String texto;
	private LocalDateTime fechaComentario;
	private Long publicacionId;

	public ComentarioDTO() {

	}

	public ComentarioDTO(String texto, LocalDateTime fechaComentario, Long publicacionId) {
		super();
		this.texto = texto;
		this.fechaComentario = fechaComentario;
		this.publicacionId = publicacionId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getFechaComentario() {
		return fechaComentario;
	}

	public void setFechaComentario(LocalDateTime fechaComentario) {
		this.fechaComentario = fechaComentario;
	}

	public Long getPublicacionId() {
		return publicacionId;
	}

	public void setPublicacionId(Long publicacionId) {
		this.publicacionId = publicacionId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fechaComentario, id, publicacionId, texto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComentarioDTO other = (ComentarioDTO) obj;
		return Objects.equals(fechaComentario, other.fechaComentario) && Objects.equals(id, other.id)
				&& Objects.equals(publicacionId, other.publicacionId) && Objects.equals(texto, other.texto);
	}

	@Override
	public String toString() {
		return "ComentarioDTO [id=" + id + ", texto=" + texto + ", fechaComentario=" + fechaComentario
				+ ", publicacionId=" + publicacionId + "]";
	}

}
