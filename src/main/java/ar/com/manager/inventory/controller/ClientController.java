package ar.com.manager.inventory.controller;

import ar.com.manager.inventory.dto.ClientDto;
import ar.com.manager.inventory.exception.NotFoundException;
import ar.com.manager.inventory.exception.ValidationException;
import ar.com.manager.inventory.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ar.com.manager.inventory.controller.util.MessageConstants.*;

@RestController
@RequestMapping("/clients")
@Tag(name = "Clientes", description = "Operaciones para la gestión de clientes")
public class ClientController {

    ClientService clientService;
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    @Operation(
            summary = "Crear nuevo cliente",
            description = "Registra un nuevo cliente en el sistema con la información proporcionada",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Cliente creado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClientDto.class),
                                    examples = @ExampleObject(
                                            name = "Cliente creado",
                                            value = """
                                            {
                                              "client": {
                                                   "id": 1,
                                                   "firstName": "Erick",
                                                   "lastName": "Lopez",
                                                   "dni": "12345678",
                                                   "sales": [],
                                                   "deleted": false
                                              }
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error de validación o datos inválidos",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Error de validación",
                                            value = """
                                            {
                                              "message": "Error saving the client",
                                              "error": "The product barcode already exists."
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Map<String,Object>> addClient(
            @Parameter(
                description = "Datos del cliente a crear",
                required = true,
                schema = @Schema(implementation = ClientDto.class))
            @Valid @RequestBody ClientDto clientDto) {
        Map<String, Object> response = new HashMap<>();
        try{
            response.put(CLIENT, clientService.addClient(clientDto));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ValidationException | NotFoundException | IllegalArgumentException e){
            response.put(MESSAGE, CLIENT_SAVE_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar cliente existente",
            description = "Actualiza la información de un cliente existente identificado por su ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cliente actualizado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClientDto.class),
                                    examples = @ExampleObject(
                                            name = "Cliente actualizado",
                                            value = """
                                            {
                                              "message": "Client successfully updated.",
                                              "client": {
                                                "id": 1,
                                                "firstName": "ErickUpdate",
                                                "lastName": "LopezUpdate",
                                                "dni": "12345690",
                                                "sales": [],
                                                "deleted": false
                                              }
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cliente no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Cliente no encontrado",
                                            value = """
                                            {
                                              "message": "Client not found",
                                              "error": "The client with id x does not exist."
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error de validación",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    name = "Cliente Vacío",
                                                    summary = "Error cuando el cliente es nulo",
                                                    value = """
                                                    {
                                                      "message": "Error updating the client",
                                                      "error": "The client cannot be null"
                                                    }
                                                    """
                                            ),
                                            @ExampleObject(
                                                    name = "DNI Duplicado",
                                                    summary = "Error cuando el DNI ya existe",
                                                    value = """
                                                    {
                                                      "message": "Error updating the client",
                                                      "error": "The client DNI already exists"
                                                    }
                                                    """
                                            )
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> updateClient(
            @Parameter(
                description = "ID único del cliente a actualizar",
                required = true,
                example = "1")
            @PathVariable Integer id,
            @Parameter(
                    description = "Nuevos datos del cliente",
                    required = true,
                    schema = @Schema(implementation = ClientDto.class)
            )
            @Valid @RequestBody ClientDto clientDto) {
        Map<String, Object> response = new HashMap<>();
        try{
            response.put(CLIENT, clientService.updateClient(clientDto, id));
            response.put(MESSAGE, CLIENT_UPDATE_SUCCESS);
            return ResponseEntity.ok(response);
        } catch (NotFoundException e){
            response.put(MESSAGE, CLIENT_NOT_FOUND);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (ValidationException e){
            response.put(MESSAGE, CLIENT_UPDATE_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar cliente",
            description = "Elimina logicamente un cliente del sistema.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Cliente eliminado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Eliminación exitosa",
                                            description = "Respuesta vacía - sin contenido",
                                            value = ""
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cliente no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Cliente no encontrado",
                                            value = """
                                            {
                                              "message": "Error deleting the client",
                                              "error": "The client does not exist"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> deleteClient(
            @Parameter(
                description = "ID único del cliente a eliminar",
                required = true,
                example = "1")
            @PathVariable Integer id) {
        try {
            clientService.deleteClient(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put(MESSAGE, CLIENT_DELETED_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener cliente por ID",
            description = "Recupera la información detallada de un cliente específico mediante su identificador único",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cliente encontrado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClientDto.class),
                                    examples = @ExampleObject(
                                            name = "Cliente encontrado",
                                            value = """
                                            {
                                              "client": {
                                                "id": 1,
                                                "firstName": "Erick",
                                                "lastName": "Lopez",
                                                "dni": "12345678",
                                                "sales": [],
                                                "deleted": false
                                              }
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cliente no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Cliente no encontrado",
                                            value = """
                                            {
                                              "message": "The client does not exist"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> getClient(
            @Parameter(
                    description = "ID único del cliente a consultar",
                    required = true,
                    example = "1"
            )
            @PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try{
            response.put(CLIENT, clientService.getClientById(id));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException e){
            response.put(MESSAGE, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping
    @Operation(
            summary = "Obtener todos los clientes",
            description = "Recupera una lista completa de todos los clientes registrados en el sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de clientes obtenida exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Lista de clientes",
                                            value = """
                                            {
                                              "clients": [
                                                  {
                                                      "id": 1,
                                                      "firstName": "Erick",
                                                      "lastName": "Lopez",
                                                      "dni": "12345678",
                                                      "sales": [],
                                                      "deleted": false
                                                  },
                                                  {
                                                      "id": 2,
                                                      "firstName": "Carlos",
                                                      "lastName": "Lopez",
                                                      "dni": "87654321",
                                                      "sales": [],
                                                      "deleted": false
                                                  }
                                              ]
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Error interno",
                                            value = """
                                            {
                                              "message": "Internal error",
                                              "error": "Error retrieving clients list"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> getAllClients() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ClientDto> clients = clientService.getAllClients();
            response.put(CLIENTS, clients);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put(MESSAGE, INTERNAL_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
