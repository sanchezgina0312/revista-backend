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

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        String descripcionPrincipal = """
                # API REST - Revista Digital
                
                Esta API permite administrar usuarios, publicaciones y comentarios dentro de una revista digital utilizando autenticación JWT y control de acceso basado en roles.

                ## Módulos del sistema
                * **Autenticación**: Login y registro
                * **Usuarios**: Gestión completa de usuarios
                * **Publicaciones**: Noticias y horóscopos
                * **Comentarios**: Comentarios asociados a publicaciones

                ## Roles y permisos
                | Rol | Descripción de Permisos |
                | :--- | :--- |
                | **USUARIO** | Consultar publicaciones |
                | **COMENTADOR** | Consultar publicaciones y crear comentarios |
                | **EDITOR** | Crear publicaciones y comentarios |
                | **ADMINISTRADOR** | Control total del sistema |

                ## Endpoints protegidos
                ### Publicaciones
                * **LISTAR / BUSCAR** → USUARIO, COMENTADOR, EDITOR, ADMINISTRADOR
                * **CREAR** → EDITOR y ADMINISTRADOR
                * **ACTUALIZAR / ELIMINAR** → ADMINISTRADOR

                ### Comentarios
                * **CREAR** → COMENTADOR, EDITOR, ADMINISTRADOR
                * **LISTAR / ELIMINAR** → ADMINISTRADOR

                ### Usuarios
                * **Todos los endpoints** → ADMINISTRADOR

                ## Códigos HTTP comunes
                * **200**: Operación exitosa | **201**: Recurso creado | **202**: Operación aceptada
                * **400**: Error en la solicitud | **401**: No autenticado | **403**: Acceso denegado
                * **404**: Recurso no encontrado | **406**: Datos inválidos | **409**: Conflicto de información
                """;

        String descripcionSeguridad = """
                Autenticación basada en JWT (JSON Web Token).

                ### ¿Cómo autenticarse?
                1. Inicia sesión usando `/auth/login`
                2. Obtén el token JWT de la respuesta
                3. Haz clic en el botón **Authorize** (candado verde)
                4. Escribe exactamente: `Bearer tu_token_jwt`
                5. Presiona **Authorize** y luego **Close**
                
                Una vez autenticado podrás consumir los endpoints protegidos según los permisos de tu rol.
                """;

        Info info = new Info()
                .title("API REST - Revista Digital")
                .version("1.0")
                .description(descripcionPrincipal)
                .contact(new Contact().name("Equipo de Desarrollo").email("revista@unbosque.edu.co").url("https://github.com/unbosque/revista-api"))
                .license(new License().name("MIT").url("https://opensource.org/licenses/MIT"));

        return new OpenAPI()
                .info(info)
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .name("JWT Authentication")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description(descripcionSeguridad))
                        .addResponses("UnauthorizedError", createResponse("Token inválido", "Unauthorized", "Token JWT inválido o expirado"))
                        .addResponses("ForbiddenError", createResponse("Sin permisos", "Forbidden", "Acceso denegado"))
                        .addResponses("NotFoundError", createResponse("No encontrado", "Not Found", "El recurso no existe"))
                        .addResponses("ConflictError", createResponse("Conflicto", "Conflict", "El recurso ya existe")));
    }

    private ApiResponse createResponse(String d, String e, String m) {
        return new ApiResponse().description(d).content(new Content().addMediaType("application/json", 
                new MediaType().addExamples("error", new Example().value("{\"error\": \""+e+"\", \"message\": \""+m+"\"}"))));
    }
}