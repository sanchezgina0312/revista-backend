package co.edu.unbosque.revista.configuration;

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
	        Usuario admin = userRepo.findByCorreo("administrador@gmail.com")
	                .orElse(new Usuario());

	        admin.setNombre("admin");
	        admin.setCorreo("administrador@gmail.com");
	        admin.setContrasenia(passwordEncoder.encode("User2026*/s"));
	        admin.setRol(Role.ADMINISTRADOR);
	        admin.setActivado(true);

	        userRepo.save(admin);
	        log.info("ADMINISTRADOR listo. Login: nombre='admin', clave='User2026*/s'");
	    };
	}
}