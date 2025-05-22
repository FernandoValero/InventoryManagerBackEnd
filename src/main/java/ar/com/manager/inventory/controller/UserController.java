package ar.com.manager.inventory.controller;

import ar.com.manager.inventory.dto.SupplierDto;
import ar.com.manager.inventory.dto.UserDto;
import ar.com.manager.inventory.exception.NotFoundException;
import ar.com.manager.inventory.exception.ValidationException;
import ar.com.manager.inventory.service.UserService;
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
@RequestMapping("/users")
@Tag(name = "Usuarios", description = "Operaciones para la gestión de usuarios del sistema")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(
            summary = "Crear nuevo usuario",
            description = "Registra un nuevo usuario en el sistema con la información proporcionada",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Usuario creado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class),
                                    examples = @ExampleObject(
                                            name = "Usuario creado",
                                            value = """
                                            {
                                              "user": {
                                                  "id": 1,
                                                  "firstName": "Carlos",
                                                  "lastName": "Gómez",
                                                  "userName": "jcgomez2023",
                                                  "password": null,
                                                  "phoneNumber": "+51987654321",
                                                  "email": "c.gomez@example.com",
                                                  "type": "USER",
                                                  "enabled": false,
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
                                    examples = {
                                            @ExampleObject(
                                                    name = "El nombre de usuario ya existe",
                                                    value = """
                                            {
                                              "message": "Error saving the user",
                                              "error": "The user username already exists"
                                            }
                                            """
                                            ),
                                            @ExampleObject(
                                            name = "El email ya existe",
                                            value = """
                                            {
                                              "message": "Error saving the user",
                                              "error": "The user email already exists"
                                            }
                                            """
                                            ),
                                            @ExampleObject(
                                                    name = "El número de telefono ya existe",
                                                    value = """
                                            {
                                              "message": "Error saving the user",
                                              "error": "The user phone number already exists."
                                            }
                                            """
                                            )
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> addUser(
            @Parameter(
                    description = "Datos del usuario a crear",
                    required = true,
                    schema = @Schema(implementation = UserDto.class))
            @Valid @RequestBody UserDto userDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put(USER, userService.addUser(userDto));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ValidationException e) {
            response.put(MESSAGE, USER_SAVE_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar usuario existente",
            description = "Actualiza la información de un usuario existente identificado por su ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario actualizado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class),
                                    examples = @ExampleObject(
                                            name = "Usuario actualizado",
                                            value = """
                                            {
                                              "message": "User successfully updated.",
                                              "user": {
                                                  "id": 1,
                                                  "firstName": "CarlosUpdate",
                                                  "lastName": "GómezUpdate",
                                                  "userName": "jcgomez2023Update",
                                                  "password": null,
                                                  "phoneNumber": "+51987654123",
                                                  "email": "c.gomez@example.comUpdate",
                                                  "type": "ADMIN",
                                                  "enabled": true,
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
                            description = "Usuario no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Usuario no encontrado",
                                            value = """
                                            {
                                              "message": "User not found",
                                              "error": "The user with id x does not exist."
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
                                                    name = "Usuario Vacío",
                                                    summary = "Error cuando el usuario es nulo",
                                                    value = """
                                                    {
                                                      "message": "Error updating the user",
                                                      "error": "The user cannot be null"
                                                    }
                                                    """
                                            ),
                                            @ExampleObject(
                                                    name = "El nombre de usuario ya existe",
                                                    value = """
                                                    {
                                                      "message": "Error saving the user",
                                                      "error": "The user username already exists"
                                                    }
                                                    """
                                            ),
                                            @ExampleObject(
                                                    name = "El email ya existe",
                                                    value = """
                                                    {
                                                      "message": "Error saving the user",
                                                      "error": "The user email already exists"
                                                    }
                                                    """
                                            ),
                                            @ExampleObject(
                                                    name = "El número de telefono ya existe",
                                                    value = """
                                                    {
                                                      "message": "Error saving the user",
                                                      "error": "The user phone number already exists."
                                                    }
                                                    """
                                            )
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> updateUser(
            @Parameter(
                    description = "ID único del usuario a actualizar",
                    required = true,
                    example = "1")
            @PathVariable Integer id,
            @Parameter(
                    description = "Nuevos datos del usuario",
                    required = true,
                    schema = @Schema(implementation = UserDto.class)
            )
            @RequestBody UserDto userDto) {
        Map<String, Object> response = new HashMap<>();
        try{
            response.put(USER, userService.updateUser(userDto, id));
            response.put(MESSAGE, USER_UPDATE_SUCCESS);
            return ResponseEntity.ok(response);
        } catch (NotFoundException e){
            response.put(MESSAGE, USER_NOT_FOUND);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (ValidationException e){
            response.put(MESSAGE, USER_UPDATE_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar usuario",
            description = "Elimina logicamente un usuario del sistema.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Usuario eliminado exitosamente",
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
                            description = "Usuario no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Usuario no encontrado",
                                            value = """
                                            {
                                              "message": "Error deleting the user",
                                              "error": "The user does not exist"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> deleteUser(
            @Parameter(
                    description = "ID único del usuario a eliminar",
                    required = true,
                    example = "1")
            @PathVariable Integer id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put(MESSAGE, USER_DELETED_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener usuario por ID",
            description = "Recupera la información detallada de un usuario específico mediante su identificador único",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario encontrado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class),
                                    examples = @ExampleObject(
                                            name = "Usuario encontrado",
                                            value = """
                                            {
                                              "user": {
                                                  "id": 1,
                                                  "firstName": "Carlos",
                                                  "lastName": "Gómez",
                                                  "userName": "jcgomez2023",
                                                  "password": null,
                                                  "phoneNumber": "+51987654321",
                                                  "email": "c.gomez@example.com",
                                                  "type": "USER",
                                                  "enabled": false,
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
                            description = "Usuario no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Usuario no encontrado",
                                            value = """
                                            {
                                              "message": "The user does not exist"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> getUser(
            @Parameter(
                    description = "ID único del usuario a consultar",
                    required = true,
                    example = "1"
            )
            @PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put(USER, userService.getUserById(id));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException e) {
            response.put(MESSAGE, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping
    @Operation(
            summary = "Obtener todos los usuarios",
            description = "Recupera una lista completa de todos los usuarios registrados en el sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de usuarios obtenida exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Lista de usuarios",
                                            value = """
                                            {
                                              "users": [
                                                    {
                                                        "id": 1,
                                                        "firstName": "Carlos",
                                                        "lastName": "Gómez",
                                                        "userName": "jcgomez2023",
                                                        "password": null,
                                                        "phoneNumber": "+51987654321",
                                                        "email": "c.gomez@example.com",
                                                        "type": "USER",
                                                        "enabled": false,
                                                        "sales": [],
                                                        "deleted": false
                                                    },
                                                    {
                                                        "id": 2,
                                                        "firstName": "Lucía",
                                                        "lastName": "Ramírez",
                                                        "userName": "lramirez2024",
                                                        "password": null,
                                                        "phoneNumber": "+541155667788",
                                                        "email": "lucia.ramirez@example.com",
                                                        "type": "USER",
                                                        "enabled": false,
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
                                              "error": "Error retrieving users list"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<UserDto> users = userService.getAllUsers();
            response.put(USERS, users);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put(MESSAGE, INTERNAL_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
