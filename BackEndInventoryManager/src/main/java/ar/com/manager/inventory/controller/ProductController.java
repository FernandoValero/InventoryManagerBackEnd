package ar.com.manager.inventory.controller;

import ar.com.manager.inventory.dto.ProductDto;
import ar.com.manager.inventory.dto.UserDto;
import ar.com.manager.inventory.exception.NotFoundException;
import ar.com.manager.inventory.exception.ValidationException;
import ar.com.manager.inventory.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ar.com.manager.inventory.controller.util.MessageConstants.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Map<String,Object>> addProduct(@RequestBody ProductDto productDto) {
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
    public ResponseEntity<Map<String, Object>> updateProduct(@PathVariable Integer id, @RequestBody ProductDto productDto) {
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
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable Integer id) {
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
    public ResponseEntity<Map<String, Object>> getProduct(@PathVariable Integer id) {
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
