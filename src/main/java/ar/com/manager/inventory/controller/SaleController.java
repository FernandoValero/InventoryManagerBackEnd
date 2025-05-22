package ar.com.manager.inventory.controller;

import ar.com.manager.inventory.dto.ClientDto;
import ar.com.manager.inventory.dto.SaleDto;
import ar.com.manager.inventory.exception.NotFoundException;
import ar.com.manager.inventory.exception.ValidationException;
import ar.com.manager.inventory.service.SaleService;
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
@RequestMapping("/sales")
@Tag(name = "Ventas", description = "Operaciones para la gestión de ventas")
public class SaleController {

    SaleService saleService;
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    @Operation(
            summary = "Crear nueva venta",
            description = "Registra una nueva venta en el sistema con la información proporcionada",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Venta creada exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SaleDto.class),
                                    examples = @ExampleObject(
                                            name = "Venta creada",
                                            value = """
                                            {
                                              "sale": {
                                                  "id": 1,
                                                  "saleDate": "21/05/2025 23:37:32",
                                                  "totalPrice": 2599.98,
                                                  "userId": 1,
                                                  "clientId": 1,
                                                  "saleDetail": [
                                                      {
                                                          "id": 1,
                                                          "amount": 2,
                                                          "product": {
                                                              "id": 1,
                                                              "number": "PROD-001",
                                                              "name": "Laptop HP EliteBook",
                                                              "stock": 48,
                                                              "barCode": "123456789012",
                                                              "price": 1299.99,
                                                              "description": "Laptop empresarial con procesador Intel Core i7 y 16GB RAM",
                                                              "category": "Electrónicos",
                                                              "image": "https://example.com/images/product.jpg",
                                                              "userId": 1,
                                                              "supplierId": null,
                                                              "deleted": false
                                                          },
                                                          "deleted": false
                                                      }
                                                  ],
                                                  "deleted": false
                                              }
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuario/Producto no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    name = "El ID de usuario no se encontró",
                                                    value = """
                                            {
                                              "message": "Error saving the sale",
                                              "error": "User not found"
                                            }
                                            """
                                            ),
                                            @ExampleObject(
                                                    name = "El ID del producto no se encontró",
                                                    value = """
                                            {
                                              "message": "Error saving the sale",
                                              "error": "The product with id x does not exist."
                                            }
                                            """
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error de validación o datos inválidos",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    name = "Datos nulos o vacios",
                                                    value = """
                                                    {
                                                      "message": "Error saving the sale",
                                                      "error": "The sale or its details cannot be null or empty"
                                                    }
                                                    """
                                            ),
                                            @ExampleObject(
                                                    name = "La cantidad de productos es menor a 1",
                                                    value = """
                                                    {
                                                      "message": "Error saving the sale",
                                                      "error": "The amount in sale details must be greater than 0"
                                                    }
                                                    """
                                            ),
                                            @ExampleObject(
                                                    name = "El ID de usuario es requerido",
                                                    value = """
                                                    {
                                                      "message": "Error saving the sale",
                                                      "error": "The user id is required"
                                                    }
                                                    """
                                            ),
                                            @ExampleObject(
                                                    name = "La cantidad de productos excede el stock disponible",
                                                    value = """
                                                    {
                                                      "message": "Error saving the sale",
                                                      "error": "The product in sale details does not have enough stock"
                                                    }
                                                    """
                                            )
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> addSale(
            @Parameter(
                    description = "Datos de la venta a crear",
                    required = true,
                    schema = @Schema(implementation = SaleDto.class))
            @Valid @RequestBody SaleDto saleDto) {
        Map<String, Object> response = new HashMap<>();
        try{
            response.put(SALE, saleService.addSale(saleDto));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ValidationException | NotFoundException | IllegalArgumentException e){
            response.put(MESSAGE, SALE_SAVE_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar venta",
            description = "Elimina logicamente una venta del sistema.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Venta eliminada exitosamente",
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
                            description = "Venta no encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Venta no encontrada",
                                            value = """
                                            {
                                              "message": "Error deleting the sale",
                                              "error": "The sale does not exist"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> deleteSale(
            @Parameter(
                    description = "ID único de la venta a eliminar",
                    required = true,
                    example = "1")
            @PathVariable Integer id) {
        try {
            saleService.deleteSale(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put(MESSAGE, SALE_DELETED_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener venta por ID",
            description = "Recupera la información detallada de una venta específica mediante su identificador único",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Venta encontrada exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SaleDto.class),
                                    examples = @ExampleObject(
                                            name = "Venta encontrada",
                                            value = """
                                            {
                                              "sale": {
                                                  "id": 1,
                                                  "saleDate": "21/05/2025 23:37:32",
                                                  "totalPrice": 2599.98,
                                                  "userId": 1,
                                                  "clientId": 1,
                                                  "saleDetail": [
                                                      {
                                                          "id": 1,
                                                          "amount": 2,
                                                          "product": {
                                                              "id": 1,
                                                              "number": "PROD-001",
                                                              "name": "Laptop HP EliteBook",
                                                              "stock": 48,
                                                              "barCode": "123456789012",
                                                              "price": 1299.99,
                                                              "description": "Laptop empresarial con procesador Intel Core i7 y 16GB RAM",
                                                              "category": "Electrónicos",
                                                              "image": "https://example.com/images/product.jpg",
                                                              "userId": 1,
                                                              "supplierId": null,
                                                              "deleted": false
                                                          },
                                                          "deleted": false
                                                      }
                                                  ],
                                                  "deleted": false
                                              }
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Venta no encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Venta no encontrada",
                                            value = """
                                            {
                                              "message": "The sale does not exist"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> getSale(
            @Parameter(
                    description = "ID único de la venta a consultar",
                    required = true,
                    example = "1"
            )
            @PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try{
            response.put(SALE, saleService.getSaleById(id));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException e){
            response.put(MESSAGE, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping
    @Operation(
            summary = "Obtener todas las ventas",
            description = "Recupera una lista completa de todas las ventas registradas en el sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de ventas obtenida exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Lista de ventas",
                                            value = """
                                            {
                                             "sales": [
                                                 {
                                                     "id": 1,
                                                     "saleDate": "21/05/2025 23:44:04",
                                                     "totalPrice": 2599.98,
                                                     "userId": 1,
                                                     "clientId": 1,
                                                     "saleDetail": [
                                                         {
                                                             "id": 1,
                                                             "amount": 2,
                                                             "product": {
                                                                 "id": 1,
                                                                 "number": "PROD-001",
                                                                 "name": "Laptop HP EliteBook",
                                                                 "stock": 48,
                                                                 "barCode": "123456789012",
                                                                 "price": 1299.99,
                                                                 "description": "Laptop empresarial con procesador Intel Core i7 y 16GB RAM",
                                                                 "category": "Electrónicos",
                                                                 "image": "https://example.com/images/product.jpg",
                                                                 "userId": 1,
                                                                 "supplierId": null,
                                                                 "deleted": false
                                                             },
                                                             "deleted": false
                                                         }
                                                     ],
                                                     "deleted": false
                                                 },
                                                 {
                                                     "id": 2,
                                                     "saleDate": "21/05/2025 23:44:12",
                                                     "totalPrice": 1049.97,
                                                     "userId": 1,
                                                     "clientId": 1,
                                                     "saleDetail": [
                                                         {
                                                             "id": 2,
                                                             "amount": 3,
                                                             "product": {
                                                                 "id": 2,
                                                                 "number": "PROD-002",
                                                                 "name": "Monitor Samsung 27\\" Curvo",
                                                                 "stock": 27,
                                                                 "barCode": "987654321098",
                                                                 "price": 349.99,
                                                                 "description": "Monitor LED curvo de 27 pulgadas con resolución Full HD y tecnología FreeSync",
                                                                 "category": "Electrónicos",
                                                                 "image": "https://example.com/images/monitor.jpg",
                                                                 "userId": 1,
                                                                 "supplierId": null,
                                                                 "deleted": false
                                                             },
                                                             "deleted": false
                                                         }
                                                     ],
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
                                              "error": "Error retrieving sales list"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> getAllSales() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<SaleDto> sales = saleService.getAllSales();
            response.put(SALES, sales);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put(MESSAGE, INTERNAL_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @GetMapping("/between")
    @Operation(
            summary = "Obtener ventas entre fechas",
            description = "Recupera todas las ventas realizadas entre dos fechas específicas",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de ventas obtenida exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SaleDto.class),
                                    examples = @ExampleObject(
                                            name = "Lista de ventas",
                                            value = """
                                            {
                                              "sales": [
                                                 {
                                                     "id": 1,
                                                     "saleDate": "21/05/2025 23:44:04",
                                                     "totalPrice": 2599.98,
                                                     "userId": 1,
                                                     "clientId": 1,
                                                     "saleDetail": [
                                                         {
                                                             "id": 1,
                                                             "amount": 2,
                                                             "product": {
                                                                 "id": 1,
                                                                 "number": "PROD-001",
                                                                 "name": "Laptop HP EliteBook",
                                                                 "stock": 48,
                                                                 "barCode": "123456789012",
                                                                 "price": 1299.99,
                                                                 "description": "Laptop empresarial con procesador Intel Core i7 y 16GB RAM",
                                                                 "category": "Electrónicos",
                                                                 "image": "https://example.com/images/product.jpg",
                                                                 "userId": 1,
                                                                 "supplierId": null,
                                                                 "deleted": false
                                                             },
                                                             "deleted": false
                                                         }
                                                     ],
                                                     "deleted": false
                                                 },
                                                 {
                                                     "id": 2,
                                                     "saleDate": "21/05/2025 23:44:12",
                                                     "totalPrice": 1049.97,
                                                     "userId": 1,
                                                     "clientId": 1,
                                                     "saleDetail": [
                                                         {
                                                             "id": 2,
                                                             "amount": 3,
                                                             "product": {
                                                                 "id": 2,
                                                                 "number": "PROD-002",
                                                                 "name": "Monitor Samsung 27\\" Curvo",
                                                                 "stock": 27,
                                                                 "barCode": "987654321098",
                                                                 "price": 349.99,
                                                                 "description": "Monitor LED curvo de 27 pulgadas con resolución Full HD y tecnología FreeSync",
                                                                 "category": "Electrónicos",
                                                                 "image": "https://example.com/images/monitor.jpg",
                                                                 "userId": 1,
                                                                 "supplierId": null,
                                                                 "deleted": false
                                                             },
                                                             "deleted": false
                                                         }
                                                     ],
                                                     "deleted": false
                                                 }
                                             ]
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Error al buscar ventas",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    name = "Fechas final menor a fecha inicial",
                                                    value = """
                                            {
                                              "message": "Internal error",
                                              "error": "The start date cannot be later than the end date."
                                            }
                                            """
                                            ),
                                            @ExampleObject(
                                                    name = "Formato de fechas incorrecto",
                                                    value = """
                                            {
                                              "message": "Internal error",
                                              "error": "The date provided does not comply with the format dd/MM/yyyy HH:mm:ss"
                                            }
                                            """
                                            )
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> getSalesBetweenDates(
            @Parameter(
                    name = "startDate",
                    description = "Fecha de inicio (formato dd/MM/yyyy)",
                    example = "01/05/2025"
            )
            @RequestParam("startDate") String startDate,
            @Parameter(
                    name = "endDate",
                    description = "Fecha de fin (formato dd/MM/yyyy)",
                    example = "31/05/2025"
            )
            @RequestParam("endDate") String endDate) {
        Map<String, Object> response = new HashMap<>();
        try{
            List<SaleDto> sales = saleService.findBySaleDateBetween(startDate,endDate);
            response.put(SALES, sales);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch(Exception e){
            response.put(MESSAGE, INTERNAL_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/client/{clientId}")
    @Operation(
            summary = "Obtener ventas por cliente",
            description = "Recupera todas las ventas asociadas a un cliente específico",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de ventas del cliente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Lista de ventas",
                                            value = """
                                            {
                                              "sales": [
                                                         {
                                                             "id": 1,
                                                             "saleDate": "21/05/2025 23:44:04",
                                                             "totalPrice": 2599.98,
                                                             "userId": 1,
                                                             "clientId": 1,
                                                             "saleDetail": [
                                                                 {
                                                                     "id": 1,
                                                                     "amount": 2,
                                                                     "product": {
                                                                         "id": 1,
                                                                         "number": "PROD-001",
                                                                         "name": "Laptop HP EliteBook",
                                                                         "stock": 48,
                                                                         "barCode": "123456789012",
                                                                         "price": 1299.99,
                                                                         "description": "Laptop empresarial con procesador Intel Core i7 y 16GB RAM",
                                                                         "category": "Electrónicos",
                                                                         "image": "https://example.com/images/product.jpg",
                                                                         "userId": 1,
                                                                         "supplierId": null,
                                                                         "deleted": false
                                                                     },
                                                                     "deleted": false
                                                                 }
                                                             ],
                                                             "deleted": false
                                                         },
                                                         {
                                                             "id": 2,
                                                             "saleDate": "21/05/2025 23:44:12",
                                                             "totalPrice": 1049.97,
                                                             "userId": 1,
                                                             "clientId": 1,
                                                             "saleDetail": [
                                                                 {
                                                                     "id": 2,
                                                                     "amount": 3,
                                                                     "product": {
                                                                         "id": 2,
                                                                         "number": "PROD-002",
                                                                         "name": "Monitor Samsung 27\\" Curvo",
                                                                         "stock": 27,
                                                                         "barCode": "987654321098",
                                                                         "price": 349.99,
                                                                         "description": "Monitor LED curvo de 27 pulgadas con resolución Full HD y tecnología FreeSync",
                                                                         "category": "Electrónicos",
                                                                         "image": "https://example.com/images/monitor.jpg",
                                                                         "userId": 1,
                                                                         "supplierId": null,
                                                                         "deleted": false
                                                                     },
                                                                     "deleted": false
                                                                 }
                                                             ],
                                                             "deleted": false
                                                         }
                                                     ]
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cliente no encontrado o error",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "Cliente no encontrado",
                                            value = """
                                            {
                                              "message": "Internal error",
                                              "error": "Client not found"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> getSalesByClientId(
            @Parameter(
                    name = "clientId",
                    description = "ID del cliente asociada a las ventas",
                    example = "1")
            @PathVariable Integer clientId) {
        Map<String, Object> response = new HashMap<>();
        try{
            List<SaleDto> sales = saleService.findByClientId(clientId);
            response.put(SALES, sales);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch(Exception e){
            response.put(MESSAGE, INTERNAL_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/user/{userId}")
    @Operation(
            summary = "Obtener ventas por usuario",
            description = "Recupera todas las ventas realizadas por un usuario específico",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de ventas del usuario",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Lista de ventas",
                                            value = """
                                            {
                                              "sales": [
                                                 {
                                                     "id": 1,
                                                     "saleDate": "21/05/2025 23:44:04",
                                                     "totalPrice": 2599.98,
                                                     "userId": 1,
                                                     "clientId": 1,
                                                     "saleDetail": [
                                                         {
                                                             "id": 1,
                                                             "amount": 2,
                                                             "product": {
                                                                 "id": 1,
                                                                 "number": "PROD-001",
                                                                 "name": "Laptop HP EliteBook",
                                                                 "stock": 48,
                                                                 "barCode": "123456789012",
                                                                 "price": 1299.99,
                                                                 "description": "Laptop empresarial con procesador Intel Core i7 y 16GB RAM",
                                                                 "category": "Electrónicos",
                                                                 "image": "https://example.com/images/product.jpg",
                                                                 "userId": 1,
                                                                 "supplierId": null,
                                                                 "deleted": false
                                                             },
                                                             "deleted": false
                                                         }
                                                     ],
                                                     "deleted": false
                                                 },
                                                 {
                                                     "id": 2,
                                                     "saleDate": "21/05/2025 23:44:12",
                                                     "totalPrice": 1049.97,
                                                     "userId": 1,
                                                     "clientId": 1,
                                                     "saleDetail": [
                                                         {
                                                             "id": 2,
                                                             "amount": 3,
                                                             "product": {
                                                                 "id": 2,
                                                                 "number": "PROD-002",
                                                                 "name": "Monitor Samsung 27\\" Curvo",
                                                                 "stock": 27,
                                                                 "barCode": "987654321098",
                                                                 "price": 349.99,
                                                                 "description": "Monitor LED curvo de 27 pulgadas con resolución Full HD y tecnología FreeSync",
                                                                 "category": "Electrónicos",
                                                                 "image": "https://example.com/images/monitor.jpg",
                                                                 "userId": 1,
                                                                 "supplierId": null,
                                                                 "deleted": false
                                                             },
                                                             "deleted": false
                                                         }
                                                     ],
                                                     "deleted": false
                                                 }
                                             ]
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuario no encontrado o error",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "Usuario no encontrado",
                                            value = """
                                            {
                                              "message": "Internal error",
                                              "error": "User not found"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> getSalesByUserId(
            @Parameter(
                    name = "userId",
                    description = "ID del usuario asociada a las ventas",
                    example = "1")
            @PathVariable Integer userId) {
        Map<String, Object> response = new HashMap<>();
        try{
            List<SaleDto> sales = saleService.findByUserId(userId);
            response.put(SALES, sales);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch(Exception e){
            response.put(MESSAGE, INTERNAL_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/month")
    @Operation(
            summary = "Obtener ventas por mes",
            description = "Recupera todas las ventas realizadas en un mes específico (1-12)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de ventas del mes",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Lista de ventas",
                                            value = """
                                                {
                                                  "sales": [
                                                         {
                                                             "id": 1,
                                                             "saleDate": "21/05/2025 23:44:04",
                                                             "totalPrice": 2599.98,
                                                             "userId": 1,
                                                             "clientId": 1,
                                                             "saleDetail": [
                                                                 {
                                                                     "id": 1,
                                                                     "amount": 2,
                                                                     "product": {
                                                                         "id": 1,
                                                                         "number": "PROD-001",
                                                                         "name": "Laptop HP EliteBook",
                                                                         "stock": 48,
                                                                         "barCode": "123456789012",
                                                                         "price": 1299.99,
                                                                         "description": "Laptop empresarial con procesador Intel Core i7 y 16GB RAM",
                                                                         "category": "Electrónicos",
                                                                         "image": "https://example.com/images/product.jpg",
                                                                         "userId": 1,
                                                                         "supplierId": null,
                                                                         "deleted": false
                                                                     },
                                                                     "deleted": false
                                                                 }
                                                             ],
                                                             "deleted": false
                                                         },
                                                         {
                                                             "id": 2,
                                                             "saleDate": "21/05/2025 23:44:12",
                                                             "totalPrice": 1049.97,
                                                             "userId": 1,
                                                             "clientId": 1,
                                                             "saleDetail": [
                                                                 {
                                                                     "id": 2,
                                                                     "amount": 3,
                                                                     "product": {
                                                                         "id": 2,
                                                                         "number": "PROD-002",
                                                                         "name": "Monitor Samsung 27\\" Curvo",
                                                                         "stock": 27,
                                                                         "barCode": "987654321098",
                                                                         "price": 349.99,
                                                                         "description": "Monitor LED curvo de 27 pulgadas con resolución Full HD y tecnología FreeSync",
                                                                         "category": "Electrónicos",
                                                                         "image": "https://example.com/images/monitor.jpg",
                                                                         "userId": 1,
                                                                         "supplierId": null,
                                                                         "deleted": false
                                                                     },
                                                                     "deleted": false
                                                                 }
                                                             ],
                                                             "deleted": false
                                                         }
                                                     ]
                                                }
                                                """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Mes inválido",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "Mes inválido",
                                            value = """
                                            {
                                              "message": "Internal error",
                                              "error": "Invalid month number. The month number must be between 1 and 12"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> getSalesByMonth(
            @Parameter(
                    name = "month",
                    description = "Número del mes (1-12)",
                    example = "5")
            @RequestParam int month){
        Map<String, Object> response = new HashMap<>();
        try{
            List<SaleDto> sales = saleService.findBySaleDateMonth(month);
            response.put(SALES, sales);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch(Exception e) {
            response.put(MESSAGE, INTERNAL_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/year")
    @Operation(
            summary = "Obtener ventas por año",
            description = "Recupera todas las ventas realizadas en un año específico",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de ventas del año",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Lista de ventas",
                                            value = """
                                            {
                                              "sales": [
                                                    {
                                                        "id": 1,
                                                        "saleDate": "21/05/2025 23:44:04",
                                                        "totalPrice": 2599.98,
                                                        "userId": 1,
                                                        "clientId": 1,
                                                        "saleDetail": [
                                                            {
                                                                "id": 1,
                                                                "amount": 2,
                                                                "product": {
                                                                    "id": 1,
                                                                    "number": "PROD-001",
                                                                    "name": "Laptop HP EliteBook",
                                                                    "stock": 48,
                                                                    "barCode": "123456789012",
                                                                    "price": 1299.99,
                                                                    "description": "Laptop empresarial con procesador Intel Core i7 y 16GB RAM",
                                                                    "category": "Electrónicos",
                                                                    "image": "https://example.com/images/product.jpg",
                                                                    "userId": 1,
                                                                    "supplierId": null,
                                                                    "deleted": false
                                                                },
                                                                "deleted": false
                                                            }
                                                        ],
                                                        "deleted": false
                                                    },
                                                    {
                                                        "id": 2,
                                                        "saleDate": "21/05/2025 23:44:12",
                                                        "totalPrice": 1049.97,
                                                        "userId": 1,
                                                        "clientId": 1,
                                                        "saleDetail": [
                                                            {
                                                                "id": 2,
                                                                "amount": 3,
                                                                "product": {
                                                                    "id": 2,
                                                                    "number": "PROD-002",
                                                                    "name": "Monitor Samsung 27\\" Curvo",
                                                                    "stock": 27,
                                                                    "barCode": "987654321098",
                                                                    "price": 349.99,
                                                                    "description": "Monitor LED curvo de 27 pulgadas con resolución Full HD y tecnología FreeSync",
                                                                    "category": "Electrónicos",
                                                                    "image": "https://example.com/images/monitor.jpg",
                                                                    "userId": 1,
                                                                    "supplierId": null,
                                                                    "deleted": false
                                                                },
                                                                "deleted": false
                                                            }
                                                        ],
                                                        "deleted": false
                                                    }
                                                ]
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Año inválido",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "Año inválido",
                                            value = """
                                            {
                                              "message": "Internal error",
                                              "error": "The year must be in a valid range (2020-9999)"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> getSalesByYear(
            @Parameter(
                    name = "year",
                    description = "Año (formato YYYY)",
                    example = "2025")
            @RequestParam int year) {
        Map<String, Object> response = new HashMap<>();
        try{
            List<SaleDto> sales = saleService.findBySaleDateYear(year);
            response.put(SALES, sales);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch(Exception e) {
            response.put(MESSAGE, INTERNAL_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @GetMapping("/product/{productId}")
    @Operation(
            summary = "Obtener ventas por producto",
            description = "Recupera todas las ventas que contienen un producto específico",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de ventas que contienen el producto",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Lista de ventas",
                                            value = """
                                            {
                                              "sales": [
                                                   {
                                                       "id": 1,
                                                       "saleDate": "21/05/2025 23:44:04",
                                                       "totalPrice": 2599.98,
                                                       "userId": 1,
                                                       "clientId": 1,
                                                       "saleDetail": [
                                                           {
                                                               "id": 1,
                                                               "amount": 2,
                                                               "product": {
                                                                   "id": 1,
                                                                   "number": "PROD-001",
                                                                   "name": "Laptop HP EliteBook",
                                                                   "stock": 48,
                                                                   "barCode": "123456789012",
                                                                   "price": 1299.99,
                                                                   "description": "Laptop empresarial con procesador Intel Core i7 y 16GB RAM",
                                                                   "category": "Electrónicos",
                                                                   "image": "https://example.com/images/product.jpg",
                                                                   "userId": 1,
                                                                   "supplierId": null,
                                                                   "deleted": false
                                                               },
                                                               "deleted": false
                                                           }
                                                       ],
                                                       "deleted": false
                                                   }
                                               ]
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Producto no encontrado o error",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "Producto no encontrado",
                                            value = """
                                            {
                                              "message": "Internal error",
                                              "error": "Product not found"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> getSalesByProduct(
            @Parameter(
                    name = "productId",
                    description = "ID del producto",
                    example = "1")
            @PathVariable Integer productId) {
        Map<String, Object> response = new HashMap<>();
        try{
            List<SaleDto> sales = saleService.findByProductId(productId);
            response.put(SALES, sales);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch(Exception e) {
            response.put(MESSAGE, INTERNAL_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
