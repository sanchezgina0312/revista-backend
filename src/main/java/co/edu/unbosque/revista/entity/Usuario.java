package co.edu.unbosque.revista.entity;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Usuario implements UserDetails{

	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique=true)
	private String nombre;
	private String correo;
	private String contrasenia;
	@Enumerated(EnumType.STRING)
	private Role rol;
	private boolean cuentaExpirada;
	private boolean cuentaBloqueada;
	private boolean credencialExpirada;
	private boolean activado;

	public Usuario() {
		this.cuentaExpirada = false;
		this.cuentaBloqueada = false;
		this.activado = false;
		this.activado = true;
		this.rol = Role.USUARIO;
	}
	
	
	public Usuario(String nombre, String correo, String contrasenia) {
		super();
		this.nombre = nombre;
		this.correo = correo;
		this.contrasenia = contrasenia;
	}

	

	public Usuario(String nombre, String correo, String contrasenia, Role rol) {
		super();
		this.nombre = nombre;
		this.correo = correo;
		this.contrasenia = contrasenia;
		this.rol = rol;
	}

	public enum Role {
		ADMINISTRADOR, 
		EDITOR,
		COMENTADOR,
		USUARIO
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROL_" + rol.name()));
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public String getContrasenia() {
		return contrasenia;
	}


	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}


	public Role getRol() {
		return rol;
	}


	public void setRol(Role rol) {
		this.rol = rol;
	}


	public boolean isCuentaExpirada() {
		return cuentaExpirada;
	}


	public void setCuentaExpirada(boolean cuentaExpirada) {
		this.cuentaExpirada = cuentaExpirada;
	}


	public boolean isCuentaBloqueada() {
		return cuentaBloqueada;
	}


	public void setCuentaBloqueada(boolean cuentaBloqueada) {
		this.cuentaBloqueada = cuentaBloqueada;
	}


	public boolean isCredencialExpirada() {
		return credencialExpirada;
	}


	public void setCredencialExpirada(boolean credencialExpirada) {
		this.credencialExpirada = credencialExpirada;
	}


	public boolean isActivado() {
		return activado;
	}


	public void setActivado(boolean activado) {
		this.activado = activado;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String getPassword() {
	    return contrasenia;
	}

	@Override
	public String getUsername() {
	    return correo;
	}

	@Override
	public boolean isAccountNonExpired() {
	    return !cuentaExpirada;
	}

	@Override
	public boolean isAccountNonLocked() {
	    return !cuentaBloqueada;
	}

	@Override
	public boolean isCredentialsNonExpired() {
	    return !credencialExpirada;
	}

	@Override
	public boolean isEnabled() {
	    return activado;
	}


	@Override
	public int hashCode() {
		return Objects.hash(activado, contrasenia, correo, credencialExpirada, cuentaBloqueada, cuentaExpirada, id,
				nombre, rol);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return activado == other.activado && Objects.equals(contrasenia, other.contrasenia)
				&& Objects.equals(correo, other.correo) && credencialExpirada == other.credencialExpirada
				&& cuentaBloqueada == other.cuentaBloqueada && cuentaExpirada == other.cuentaExpirada
				&& Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre) && rol == other.rol;
	}


	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", correo=" + correo + ", contrasenia=" + contrasenia
				+ ", rol=" + rol + ", cuentaExpirada=" + cuentaExpirada + ", cuentaBloqueada=" + cuentaBloqueada
				+ ", credencialExpirada=" + credencialExpirada + ", activado=" + activado + "]";
	}
	
	
}