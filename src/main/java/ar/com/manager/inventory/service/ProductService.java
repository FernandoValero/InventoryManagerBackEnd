package ar.com.manager.inventory.service;

import ar.com.manager.inventory.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto addProduct(ProductDto productDto);
    ProductDto updateProduct(ProductDto productDto, Integer id);
    void deleteProduct(Integer id);
    ProductDto getProductById(Integer id);
    List<ProductDto> getAllProducts();

}
