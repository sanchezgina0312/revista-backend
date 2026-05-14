package co.edu.unbosque.revista.configuration;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import co.edu.unbosque.revista.entity.Usuario;
import co.edu.unbosque.revista.entity.Usuario.Role;
import co.edu.unbosque.revista.repository.UsuarioRepository;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Value("${app.default.user.password}")
	private String defaultPassword;

	@Bean
	CommandLineRunner initDatabase(UsuarioRepository userRepo, PasswordEncoder passwordEncoder) {
		return args -> {

			Optional<Usuario> adminEncontrado = userRepo.findByNombre("Administrador Principal");

			if (adminEncontrado.isPresent()) {
				log.info("El administrador principal ya existe, saltando...");
			} else {
				Usuario admin = new Usuario();
				admin.setNombre("Administrador Principal");
				admin.setCorreo("administrador@gmail.com");
				admin.setContrasenia(passwordEncoder.encode(defaultPassword));
				admin.setRol(Role.ADMINISTRADOR);

				userRepo.save(admin);
				log.info("Precargando usuario ADMINISTRADOR");
			}

			Optional<Usuario> usuarioEncontrado = userRepo.findByNombre("Usuario");

			if (usuarioEncontrado.isPresent()) {
				log.info("El usuario normal ya existe, saltando...");
			} else {
				Usuario normalUser = new Usuario();
				normalUser.setNombre("Usuario");
				normalUser.setCorreo("usuario@gmail.com");
				normalUser.setContrasenia(passwordEncoder.encode(defaultPassword));
				normalUser.setRol(Role.USUARIO);

				userRepo.save(normalUser);
				log.info("Precargando usuario USUARIO");
			}
		};
	}
}