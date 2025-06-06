package ar.com.manager.inventory.controller;

import ar.com.manager.inventory.dto.SupplierDto;
import ar.com.manager.inventory.exception.NotFoundException;
import ar.com.manager.inventory.exception.ValidationException;
import ar.com.manager.inventory.service.SupplierService;
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
@RequestMapping("/suppliers")
@Tag(name = "Proveedores", description = "Operaciones para la gestión de proveedores")
public class SupplierController {

    SupplierService supplierService;
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    @Operation(
            summary = "Crear nuevo proveedor",
            description = "Registra un nuevo proveedor en el sistema con la información proporcionada",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Proveedor creado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SupplierDto.class),
                                    examples = @ExampleObject(
                                            name = "Proveedor creado",
                                            value = """
                                            {
                                              "supplier": {
                                                   "id": 1,
                                                   "firstName": "Juan",
                                                   "lastName": "Pérez",
                                                   "phoneNumber": "+51987654321",
                                                   "email": "juan.perez@example.com",
                                                   "company": "TecnoSuministros S.A."
                                                   "products": [],
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
                                                    name = "El email ya existe",
                                                    value = """
                                            {
                                              "message": "Error saving the supplier",
                                              "error": "The supplier email already exists."
                                            }
                                            """
                                            ),
                                            @ExampleObject(
                                                    name = "El número de telefono ya existe",
                                                    value = """
                                            {
                                              "message": "Error saving the supplier",
                                              "error": "The supplier phone number already exists."
                                            }
                                            """
                                            )
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> addSupplier(
            @Parameter(
                    description = "Datos del proveedor a crear",
                    required = true,
                    schema = @Schema(implementation = SupplierDto.class))
            @Valid @RequestBody SupplierDto supplierDto){
        Map<String, Object> response = new HashMap<>();
        try{
            response.put(SUPPLIER, supplierService.addSupplier(supplierDto));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ValidationException | NotFoundException | IllegalArgumentException e) {
            response.put(MESSAGE, SUPPLIER_SAVE_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar proveedor existente",
            description = "Actualiza la información de un proveedor existente identificado por su ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Proveedor actualizado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SupplierDto.class),
                                    examples = @ExampleObject(
                                            name = "Proveedor actualizado",
                                            value = """
                                            {
                                              "message": "Supplier successfully updated.",
                                              "supplier": {
                                                   "id": 1,
                                                   "firstName": "JuanUpdate",
                                                   "lastName": "PérezUpdate",
                                                   "phoneNumber": "+51987654123",
                                                   "email": "juan.perez@example.comUpdate",
                                                   "company": "TecnoSuministros S.A.Update"
                                                   "products": [],
                                                   "deleted": false
                                              }
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Proveedor no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Proveedor no encontrado",
                                            value = """
                                            {
                                              "message": "Supplier not found",
                                              "error": "The supplier with id x does not exist."
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
                                                    name = "Proveedor Vacío",
                                                    summary = "Error cuando el proveedor es nulo",
                                                    value = """
                                                    {
                                                      "message": "Error updating the supplier",
                                                      "error": "The supplier cannot be null"
                                                    }
                                                    """
                                            ),
                                            @ExampleObject(
                                                    name = "El email ya existe",
                                                    summary = "Error cuando el email ya existe",
                                                    value = """
                                                    {
                                                      "message": "Error updating the supplier",
                                                      "error": "The supplier email already exists."
                                                    }
                                                    """
                                            ),
                                            @ExampleObject(
                                                    name = "El número de telefono ya existe",
                                                    summary = "Error cuando el número de telefono ya existe",
                                                    value = """
                                                    {
                                                      "message": "Error updating the supplier",
                                                      "error": "The supplier phone number already exists."
                                                    }
                                                    """
                                            )
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> updateSupplier(
            @Parameter(
                    description = "ID único del proveedor a actualizar",
                    required = true,
                    example = "1")
            @PathVariable Integer id,
            @Parameter(
                    description = "Nuevos datos del proveedor",
                    required = true,
                    schema = @Schema(implementation = SupplierDto.class)
            )
            @RequestBody SupplierDto supplierDto){
        Map<String, Object> response = new HashMap<>();
        try{
            response.put(SUPPLIER, supplierService.updateSupplier(supplierDto, id));
            response.put(MESSAGE, SUPPLIER_UPDATE_SUCCESS);
            return ResponseEntity.ok(response);
        } catch (NotFoundException e){
            response.put(MESSAGE, SUPPLIER_NOT_FOUND);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (ValidationException e){
            response.put(MESSAGE, SUPPLIER_UPDATE_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar proveedor",
            description = "Elimina logicamente un proveedor del sistema.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Proveedor eliminado exitosamente",
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
                            description = "Proveedor no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Proveedor no encontrado",
                                            value = """
                                            {
                                              "message": "Error deleting the supplier",
                                              "error": "The supplier does not exist"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> deleteSupplier(
            @Parameter(
                    description = "ID único del proveedor a eliminar",
                    required = true,
                    example = "1")
            @PathVariable Integer id){
        try {
            supplierService.deleteSupplier(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put(MESSAGE, SUPPLIER_DELETED_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener proveedor por ID",
            description = "Recupera la información detallada de un proveedor específico mediante su identificador único",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Proveedor encontrado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SupplierDto.class),
                                    examples = @ExampleObject(
                                            name = "Proveedor encontrado",
                                            value = """
                                            {
                                              "supplier": {
                                                   "id": 1,
                                                   "firstName": "Juan",
                                                   "lastName": "Pérez",
                                                   "phoneNumber": "+51987654321",
                                                   "email": "juan.perez@example.com",
                                                   "company": "TecnoSuministros S.A."
                                                   "products": [],
                                                   "deleted": false
                                              }
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Proveedor no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Proveedor no encontrado",
                                            value = """
                                            {
                                              "message": "The supplier does not exist"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> getSupplier(
            @Parameter(
                    description = "ID único del proveedor a consultar",
                    required = true,
                    example = "1"
            )
            @PathVariable Integer id){
        Map<String, Object> response = new HashMap<>();
        try {
            response.put(SUPPLIER, supplierService.getSupplierById(id));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException e) {
            response.put(MESSAGE, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping
    @Operation(
            summary = "Obtener todos los proveedores",
            description = "Recupera una lista completa de todos los proveedores registrados en el sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de proveedores obtenida exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Lista de proveedores",
                                            value = """
                                            {
                                              "suppliers": [
                                                   {
                                                       "id": 1,
                                                       "firstName": "Juan",
                                                       "lastName": "Pérez",
                                                       "phoneNumber": "+51987654321",
                                                       "email": "juan.perez@example.com",
                                                       "company": "TecnoSuministros S.A.",
                                                       "products": [],
                                                       "deleted": false
                                                   },
                                                   {
                                                       "id": 2,
                                                       "firstName": "María",
                                                       "lastName": "González",
                                                       "phoneNumber": "+541123456789",
                                                       "email": "maria.gonzalez@solucionesit.com",
                                                       "company": "Soluciones IT SRL",
                                                       "products": [],
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
                                              "error": "Error retrieving suppliers list"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> getAllSuppliers(){
        Map<String, Object> response = new HashMap<>();
        try {
            List<SupplierDto> suppliers = supplierService.getAllSuppliers();
            response.put(SUPPLIERS, suppliers);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put(MESSAGE, INTERNAL_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
