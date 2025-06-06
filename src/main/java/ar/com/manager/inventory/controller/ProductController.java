package ar.com.manager.inventory.controller;

import ar.com.manager.inventory.dto.ClientDto;
import ar.com.manager.inventory.dto.ProductDto;
import ar.com.manager.inventory.dto.UserDto;
import ar.com.manager.inventory.exception.NotFoundException;
import ar.com.manager.inventory.exception.ValidationException;
import ar.com.manager.inventory.service.ProductService;
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
@RequestMapping("/products")
@Tag(name = "Productos", description = "Operaciones para la gestión de productos")
public class ProductController {

    ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(
            summary = "Crear nuevo producto",
            description = "Registra un nuevo producto en el sistema con la información proporcionada",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Producto creado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDto.class),
                                    examples = @ExampleObject(
                                            name = "Producto creado",
                                            value = """
                                            {
                                              "product": {
                                                 "id": 1,
                                                 "number": "PROD-001",
                                                 "name": "Laptop HP EliteBook",
                                                 "stock": 50,
                                                 "barCode": "123456789012",
                                                 "price": 1299.99,
                                                 "description": "Laptop empresarial con procesador Intel Core i7 y 16GB RAM",
                                                 "category": "Electrónicos",
                                                 "image": "https://example.com/images/product.jpg",
                                                 "userId": 1,
                                                 "supplierId": null,
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
                                            name = "El ID de usuario no se encontró",
                                            value = """
                                            {
                                              "message": "Error saving the product",
                                              "error": "User not found"
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
                                                    name = "El codigo de barras ya existe",
                                                    value = """
                                                            {
                                                              "message": "Error saving the product",
                                                              "error": "The product barcode already exists."
                                                            }
                                                            """
                                            ),
                                            @ExampleObject(
                                                    name = "El número de producto ya existe",
                                                    value = """
                                                            {
                                                              "message": "Error saving the product",
                                                              "error": "The product number already exists."
                                                            }
                                                            """
                                            ),
                                            @ExampleObject(
                                                    name = "El ID de usuario es requerido",
                                                    value = """
                                                            {
                                                              "message": "Error saving the product",
                                                              "error": "The user id is required"
                                                            }
                                                            """
                                            ),
                                            @ExampleObject(
                                                    name = "El precio ingresado es incorrecto",
                                                    value = """
                                                            {
                                                              "message": "Error saving the product",
                                                              "error": "The price must be greater than 0."
                                                            }
                                                            """
                                            )
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<Map<String,Object>> addProduct(
            @Parameter(
                    description = "Datos del producto a crear",
                    required = true,
                    schema = @Schema(implementation = ProductDto.class))
            @Valid @RequestBody ProductDto productDto) {
        Map<String,Object> response = new HashMap<>();
        try {
            response.put(PRODUCT, productService.addProduct(productDto));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ValidationException | NotFoundException | IllegalArgumentException e) {
            response.put(MESSAGE, PRODUCT_SAVE_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar producto existente",
            description = "Actualiza la información de un producto existente identificado por su ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Producto actualizado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDto.class),
                                    examples = @ExampleObject(
                                            name = "Producto actualizado",
                                            value = """
                                            {
                                              "message": "Product successfully updated.",
                                              "product": {
                                                 "id": 1,
                                                 "number": "PROD-098",
                                                 "name": "Laptop HP EliteBookUpdate",
                                                 "stock": 500,
                                                 "barCode": "1234567898765",
                                                 "price": 5000.99,
                                                 "description": "Laptop empresarial con procesador Intel Core i7 y 16GB RAM Update",
                                                 "category": "Laptops",
                                                 "image": "https://example.com/images/product.jpgUpdate",
                                                 "userId": 1,
                                                 "supplierId": 1,
                                                 "deleted": false
                                              }
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Producto no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Producto no encontrado",
                                            value = """
                                            {
                                              "message": "Product not found",
                                              "error": "The product with id x does not exist."
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
                                                    name = "Producto Vacío",
                                                    summary = "Error cuando el producto es nulo",
                                                    value = """
                                                    {
                                                      "message": "Error updating the product",
                                                      "error": "The product cannot be null"
                                                    }
                                                    """
                                            ),
                                            @ExampleObject(
                                                    name = "Número de producto duplicado",
                                                    summary = "Error cuando el número de producto ya existe",
                                                    value = """
                                                    {
                                                      "message": "Error updating the product",
                                                      "error": "The product number already exists"
                                                    }
                                                    """
                                            ),
                                            @ExampleObject(
                                                    name = "Código de barras de producto duplicado",
                                                    summary = "Error cuando el código de barras del producto ya existe",
                                                    value = """
                                                    {
                                                      "message": "Error updating the product",
                                                      "error": "The product barcode already exists"
                                                    }
                                                    """
                                            )
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> updateProduct(
            @Parameter(
                    description = "ID único del producto a actualizar",
                    required = true,
                    example = "1")
            @PathVariable Integer id,
            @Parameter(
                    description = "Nuevos datos del producto",
                    required = true,
                    schema = @Schema(implementation = ProductDto.class)
            )
            @Valid
            @RequestBody ProductDto productDto) {
        Map<String, Object> response = new HashMap<>();
        try{
            response.put(PRODUCT, productService.updateProduct(productDto, id));
            response.put(MESSAGE, PRODUCT_UPDATE_SUCCESS);
            return ResponseEntity.ok(response);
        } catch (NotFoundException e){
            response.put(MESSAGE, PRODUCT_NOT_FOUND);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (ValidationException e){
            response.put(MESSAGE, PRODUCT_UPDATE_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar producto",
            description = "Elimina logicamente un producto del sistema.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Producto eliminado exitosamente",
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
                            description = "Producto no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Producto no encontrado",
                                            value = """
                                            {
                                              "message": "Error deleting the product",
                                              "error": "The product does not exist"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> deleteProduct(
            @Parameter(
                    description = "ID único del producto a eliminar",
                    required = true,
                    example = "1")
            @PathVariable Integer id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put(MESSAGE, PRODUCT_DELETED_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener producto por ID",
            description = "Recupera la información detallada de un producto específico mediante su identificador único",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Producto encontrado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDto.class),
                                    examples = @ExampleObject(
                                            name = "Producto encontrado",
                                            value = """
                                            {
                                              "product": {
                                                 "id": 1,
                                                 "number": "PROD-001",
                                                 "name": "Laptop HP EliteBook",
                                                 "stock": 50,
                                                 "barCode": "123456789012",
                                                 "price": 1299.99,
                                                 "description": "Laptop empresarial con procesador Intel Core i7 y 16GB RAM",
                                                 "category": "Electrónicos",
                                                 "image": "https://example.com/images/product.jpg",
                                                 "userId": 1,
                                                 "supplierId": null,
                                                 "deleted": false
                                              }
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Producto no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Producto no encontrado",
                                            value = """
                                            {
                                              "message": "The product does not exist"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> getProduct(
            @Parameter(
                    description = "ID único del producto a consultar",
                    required = true,
                    example = "1"
            )
            @PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try{
            response.put(PRODUCT, productService.getProductById(id));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException e){
            response.put(MESSAGE, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping
    @Operation(
            summary = "Obtener todos los productos",
            description = "Recupera una lista completa de todos los productos registrados en el sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de productos obtenida exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Lista de productos",
                                            value = """
                                            {
                                              "products": [
                                                  {
                                                      "id": 1,
                                                      "number": "PROD-001",
                                                      "name": "Laptop HP EliteBook",
                                                      "stock": 50,
                                                      "barCode": "123456789012",
                                                      "price": 1299.99,
                                                      "description": "Laptop empresarial con procesador Intel Core i7 y 16GB RAM",
                                                      "category": "Electrónicos",
                                                      "image": "https://example.com/images/product.jpg",
                                                      "userId": 1,
                                                      "supplierId": null,
                                                      "deleted": false
                                                  },
                                                  {
                                                      "id": 2,
                                                      "number": "PROD-002",
                                                      "name": "Monitor Samsung 27\\" Curvo",
                                                      "stock": 30,
                                                      "barCode": "987654321098",
                                                      "price": 349.99,
                                                      "description": "Monitor LED curvo de 27 pulgadas con resolución Full HD y tecnología FreeSync",
                                                      "category": "Electrónicos",
                                                      "image": "https://example.com/images/monitor.jpg",
                                                      "userId": 1,
                                                      "supplierId": null,
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
                                              "error": "Error retrieving products list"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, Object>> getAllProducts() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<ProductDto> products = productService.getAllProducts();
            response.put(PRODUCTS, products);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put(MESSAGE, INTERNAL_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
