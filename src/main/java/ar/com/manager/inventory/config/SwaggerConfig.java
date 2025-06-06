package ar.com.manager.inventory.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de " + applicationName)
                        .description("Sistema de Gestión de Inventario y Ventas - Este servicio proporciona una interfaz completa para la administración de productos, clientes, proveedores, usuarios y transacciones de ventas. Permite realizar operaciones CRUD en todas las entidades del sistema, gestionar el stock de productos y procesar ventas.")
                        .version("1.0"))
                .servers(Arrays.asList(
                        new Server().url("/api/v1/").description("Inventory Service")
                ))
                .tags(Arrays.asList(
                        new Tag().name("Productos").description("Operaciones relacionadas con la gestión de productos e inventario"),
                        new Tag().name("Clientes").description("Gestión de datos de clientes"),
                        new Tag().name("Proveedores").description("Administración de proveedores"),
                        new Tag().name("Usuarios").description("Gestión de usuarios del sistema"),
                        new Tag().name("Ventas").description("Procesamiento de ventas y actualizacion de stock")
                ));
    }
}

