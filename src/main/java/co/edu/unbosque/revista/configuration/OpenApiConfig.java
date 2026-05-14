package co.edu.unbosque.revista.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración principal de OpenAPI (Swagger) para la API REST de la revista digital.
 *
 * <p>
 * Esta clase centraliza:
 * </p>
 *
 * <ul>
 *   <li>Información general de la API</li>
 *   <li>Configuración de autenticación JWT</li>
 *   <li>Descripción de roles y permisos</li>
 *   <li>Respuestas reutilizables</li>
 *   <li>Documentación interactiva Swagger UI</li>
 * </ul>
 *
 * <h2>Roles disponibles en el sistema</h2>
 *
 * <ul>
 *   <li><b>USUARIO</b>: Puede consultar publicaciones</li>
 *   <li><b>COMENTADOR</b>: Puede crear comentarios</li>
 *   <li><b>EDITOR</b>: Puede crear publicaciones y comentarios</li>
 *   <li><b>ADMINISTRADOR</b>: Control total del sistema</li>
 * </ul>
 *
 * <h2>Flujo básico de autenticación</h2>
 *
 * <ol>
 *   <li>Registrar usuario usando <code>/auth/register</code></li>
 *   <li>Iniciar sesión usando <code>/auth/login</code></li>
 *   <li>Copiar el token JWT recibido</li>
 *   <li>Usar el botón "Authorize" de Swagger</li>
 *   <li>Ingresar: <code>Bearer tu_token</code></li>
 * </ol>
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configuración personalizada de OpenAPI.
     *
     * @return objeto OpenAPI configurado
     */
    @Bean
    public OpenAPI customOpenAPI() {

        String descripcionPrincipal =
                """
                <h1>API REST - Revista Digital</h1>

                <p>
                Esta API permite administrar usuarios, publicaciones y comentarios
                dentro de una revista digital utilizando autenticación JWT y control
                de acceso basado en roles.
                </p>

                <h2>Módulos del sistema</h2>

                <ul>
                    <li><b>Autenticación</b>: Login y registro</li>
                    <li><b>Usuarios</b>: Gestión completa de usuarios</li>
                    <li><b>Publicaciones</b>: Noticias y horóscopos</li>
                    <li><b>Comentarios</b>: Comentarios asociados a publicaciones</li>
                </ul>

                <h2>Roles y permisos</h2>

                <table border="1" cellpadding="5">
                    <tr>
                        <th>Rol</th>
                        <th>Permisos</th>
                    </tr>

                    <tr>
                        <td>USUARIO</td>
                        <td>Consultar publicaciones</td>
                    </tr>

                    <tr>
                        <td>COMENTADOR</td>
                        <td>Consultar publicaciones y crear comentarios</td>
                    </tr>

                    <tr>
                        <td>EDITOR</td>
                        <td>Crear publicaciones y comentarios</td>
                    </tr>

                    <tr>
                        <td>ADMINISTRADOR</td>
                        <td>Control total del sistema</td>
                    </tr>
                </table>

                <h2>Endpoints protegidos</h2>

                <ul>
                    <li>
                        <b>Publicaciones</b>
                        <ul>
                            <li>LISTAR / BUSCAR → USUARIO, COMENTADOR, EDITOR, ADMINISTRADOR</li>
                            <li>CREAR → EDITOR y ADMINISTRADOR</li>
                            <li>ACTUALIZAR / ELIMINAR → ADMINISTRADOR</li>
                        </ul>
                    </li>

                    <li>
                        <b>Comentarios</b>
                        <ul>
                            <li>CREAR → COMENTADOR, EDITOR, ADMINISTRADOR</li>
                            <li>LISTAR / ELIMINAR → ADMINISTRADOR</li>
                        </ul>
                    </li>

                    <li>
                        <b>Usuarios</b>
                        <ul>
                            <li>Todos los endpoints → ADMINISTRADOR</li>
                        </ul>
                    </li>
                </ul>

                <h2>Códigos HTTP comunes</h2>

                <ul>
                    <li><b>200</b>: Operación exitosa</li>
                    <li><b>201</b>: Recurso creado</li>
                    <li><b>202</b>: Operación aceptada</li>
                    <li><b>400</b>: Error en la solicitud</li>
                    <li><b>401</b>: No autenticado</li>
                    <li><b>403</b>: Acceso denegado</li>
                    <li><b>404</b>: Recurso no encontrado</li>
                    <li><b>406</b>: Datos inválidos</li>
                    <li><b>409</b>: Conflicto de información</li>
                </ul>
                """;

        String descripcionSeguridad =
                """
                Autenticación basada en JWT (JSON Web Token).

                <h3>¿Cómo autenticarse?</h3>

                <ol>
                    <li>Inicia sesión usando <code>/auth/login</code></li>
                    <li>Obtén el token JWT</li>
                    <li>Haz clic en el botón <b>Authorize</b></li>
                    <li>Escribe:
                        <code>Bearer tu_token_jwt</code>
                    </li>
                    <li>Presiona Authorize</li>
                </ol>

                <p>
                Una vez autenticado podrás consumir los endpoints protegidos
                según los permisos de tu rol.
                </p>
                """;

        Info info = new Info()
                .title("API REST - Revista Digital")
                .version("1.0")
                .description(descripcionPrincipal)
                .contact(
                        new Contact()
                                .name("Equipo de Desarrollo")
                                .email("revista@unbosque.edu.co")
                                .url("https://github.com/unbosque/revista-api")
                )
                .license(
                        new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")
                );

        SecurityScheme securityScheme = new SecurityScheme()
                .name("JWT Authentication")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description(descripcionSeguridad);

        return new OpenAPI()
                .info(info)

                // Seguridad global
                .addSecurityItem(
                        new SecurityRequirement().addList("bearerAuth")
                )

                .components(
                        new Components()

                                // JWT
                                .addSecuritySchemes(
                                        "bearerAuth",
                                        securityScheme
                                )

                                // 401
                                .addResponses(
                                        "UnauthorizedError",
                                        new ApiResponse()
                                                .description("Token inválido o expirado")
                                                .content(
                                                        new Content()
                                                                .addMediaType(
                                                                        "application/json",
                                                                        new MediaType()
                                                                                .addExamples(
                                                                                        "error",
                                                                                        new Example()
                                                                                                .value("""
                                                                                                    {
                                                                                                      "error": "Unauthorized",
                                                                                                      "message": "Token JWT inválido o expirado"
                                                                                                    }
                                                                                                    """)
                                                                                )
                                                                )
                                                )
                                )

                                // 403
                                .addResponses(
                                        "ForbiddenError",
                                        new ApiResponse()
                                                .description("No tienes permisos suficientes")
                                                .content(
                                                        new Content()
                                                                .addMediaType(
                                                                        "application/json",
                                                                        new MediaType()
                                                                                .addExamples(
                                                                                        "error",
                                                                                        new Example()
                                                                                                .value("""
                                                                                                    {
                                                                                                      "error": "Forbidden",
                                                                                                      "message": "Acceso denegado para este recurso"
                                                                                                    }
                                                                                                    """)
                                                                                )
                                                                )
                                                )
                                )

                                // 404
                                .addResponses(
                                        "NotFoundError",
                                        new ApiResponse()
                                                .description("Recurso no encontrado")
                                                .content(
                                                        new Content()
                                                                .addMediaType(
                                                                        "application/json",
                                                                        new MediaType()
                                                                                .addExamples(
                                                                                        "error",
                                                                                        new Example()
                                                                                                .value("""
                                                                                                    {
                                                                                                      "error": "Not Found",
                                                                                                      "message": "El recurso solicitado no existe"
                                                                                                    }
                                                                                                    """)
                                                                                )
                                                                )
                                                )
                                )

                                // 409
                                .addResponses(
                                        "ConflictError",
                                        new ApiResponse()
                                                .description("Conflicto de información")
                                                .content(
                                                        new Content()
                                                                .addMediaType(
                                                                        "application/json",
                                                                        new MediaType()
                                                                                .addExamples(
                                                                                        "error",
                                                                                        new Example()
                                                                                                .value("""
                                                                                                    {
                                                                                                      "error": "Conflict",
                                                                                                      "message": "El recurso ya existe"
                                                                                                    }
                                                                                                    """)
                                                                                )
                                                                )
                                                )
                                )
                );
    }
}