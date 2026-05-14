package co.edu.unbosque.revista.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad de la aplicación.
 * Maneja autenticación JWT y autorización basada en roles.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Filtro JWT personalizado.
     */
    private final JwtAuthenticationFilter jwtAuthFilter;

    /**
     * Servicio para cargar usuarios desde la base de datos.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param jwtAuthFilter filtro JWT
     * @param userDetailsService servicio de usuarios
     */
    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthFilter,
            UserDetailsService userDetailsService) {

        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Configuración principal de seguridad HTTP.
     *
     * @param http configuración HTTP
     * @return cadena de filtros de seguridad
     * @throws Exception excepción de configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                // Endpoints públicos
                .requestMatchers("/auth/**").permitAll()

                // Swagger
                .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**")
                .permitAll()

                // Usuarios autenticados
                .requestMatchers(
                        "/user/getall",
                        "/user/count",
                        "/user/exists/**",
                        "/user/getbyid/**")
                .hasAnyRole("USUARIO", "ADMINISTRADOR")

                // Solo administrador
                .requestMatchers("/user/**")
                .hasRole("ADMINISTRADOR")

                // Todo lo demás requiere autenticación
                .anyRequest()
                .authenticated()
            )

            // API Stateless con JWT
            .sessionManagement(session ->
                    session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS))

            // Proveedor de autenticación
            .authenticationProvider(authenticationProvider())

            // Filtro JWT
            .addFilterBefore(
                    jwtAuthFilter,
                    UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Proveedor de autenticación usando UserDetailsService.
     *
     * @return AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider =
                new DaoAuthenticationProvider(userDetailsService);

        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /**
     * AuthenticationManager de Spring.
     *
     * @param config configuración de autenticación
     * @return AuthenticationManager
     * @throws Exception excepción de configuración
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config)
            throws Exception {

        return config.getAuthenticationManager();
    }

    /**
     * Codificador BCrypt para contraseñas.
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
