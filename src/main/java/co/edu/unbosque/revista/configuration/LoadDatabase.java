package co.edu.unbosque.revista.configuration;

import co.edu.unbosque.revista.entity.Usuario;
import co.edu.unbosque.revista.repository.UsuarioRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase de configuración para cargar datos iniciales en la base de datos. Crea usuarios
 * predeterminados (administrador y usuario normal) al iniciar la aplicación si estos no existen
 * previamente.
 */
@Configuration
public class LoadDatabase {
  /** Logger para registrar mensajes durante la carga de datos. */
  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
  
  /** Contraseña por defecto obtenida desde application.properties */
  @Value("${app.default.user.password}")
  private String defaultPassword;


  /**
   * Inicializa la base de datos con usuarios predeterminados. Crea un usuario administrador y un
   * usuario normal si no existen.
   *
   * @param userRepo Repositorio de usuarios para acceder a la base de datos
   * @param passwordEncoder Codificador de contraseñas para encriptar las contraseñas de los
   *     usuarios
   * @return Un CommandLineRunner que se ejecuta al iniciar la aplicación
   */
  @Bean
  CommandLineRunner initDatabase(UsuarioRepository userRepo, PasswordEncoder passwordEncoder) {

    return args -> {
      Optional<Usuario> found = userRepo.findByNombre("Administrador Principal");
      if (found.isPresent()) {
        log.info("El administrador principal ya existe, omitiendo la creación del administrador...");
      } else {
        Usuario adminUser = new Usuario("Administrador Principal", "administrador@gmail.com", passwordEncoder.encode(defaultPassword), Usuario.Role.ADMINISTRADOR);
        userRepo.save(adminUser);
        log.info("Precargando usuario administrador");
      }
      Optional<Usuario> found2 = userRepo.findByNombre("Usuario Normal");
      if (found2.isPresent()) {
        log.info("El usuario normal ya existe, omitiendo la creación del usuario normal...");
      } else {
        Usuario normalUser =
            new Usuario("Usuario Normal", "usuario@gmail.com",passwordEncoder.encode(defaultPassword), Usuario.Role.USUARIO);
        userRepo.save(normalUser);
        log.info("Precargando usuario normal");
      }
    };
  }
}
