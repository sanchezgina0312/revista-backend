package co.edu.unbosque.revista.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Comentario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String texto;
	private LocalDateTime fechaComentario;
	private Long publicacionId;
	private Long editorId;

	public Comentario() {

	}

	public Comentario(String texto, LocalDateTime fechaComentario, Long publicacionId, Long editorId) {
		super();
		this.texto = texto;
		this.fechaComentario = fechaComentario;
		this.publicacionId = publicacionId;
		this.editorId = editorId;
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
		return Objects.hash(editorId, fechaComentario, id, publicacionId, texto);
	}

	public Long getEditorId() {
		return editorId;
	}

	public void setEditorId(Long editorId) {
		this.editorId = editorId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comentario other = (Comentario) obj;
		return Objects.equals(editorId, other.editorId) && Objects.equals(fechaComentario, other.fechaComentario)
				&& Objects.equals(id, other.id) && Objects.equals(publicacionId, other.publicacionId)
				&& Objects.equals(texto, other.texto);
	}

	@Override
	public String toString() {
		return "Comentario [id=" + id + ", texto=" + texto + ", fechaComentario=" + fechaComentario + ", publicacionId="
				+ publicacionId + ", editorId=" + editorId + "]";
	}

}
