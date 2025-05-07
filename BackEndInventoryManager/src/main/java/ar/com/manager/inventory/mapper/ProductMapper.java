package ar.com.manager.inventory.mapper;

import ar.com.manager.inventory.dto.ProductDto;
import ar.com.manager.inventory.entity.Product;
import ar.com.manager.inventory.exception.NotFoundException;
import ar.com.manager.inventory.repository.SupplierRepository;
import ar.com.manager.inventory.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    UserRepository userRepository;
    SupplierRepository supplierRepository;
    public ProductMapper(UserRepository userRepository, SupplierRepository supplierRepository) {
        this.userRepository = userRepository;
        this.supplierRepository = supplierRepository;
    }

    public ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setNumber(product.getNumber());
        productDto.setName(product.getName());
        productDto.setStock(product.getStock());
        productDto.setBarCode(product.getBarCode());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(product.getCategory());
        productDto.setImage(product.getImage());
        productDto.setUserId(product.getUser().getId());
        if(product.getSupplier() != null){
            productDto.setSupplierId(product.getSupplier().getId());
        }
        return productDto;
    }
    public Product toEntity(ProductDto productDto) throws NotFoundException {
        Product product = new Product();
        product.setNumber(productDto.getNumber());
        product.setName(productDto.getName());
        product.setStock(productDto.getStock());
        product.setBarCode(productDto.getBarCode());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCategory(productDto.getCategory());
        product.setImage(productDto.getImage());
        if(productDto.getUserId()!=null){
            product.setUser(userRepository.findById(productDto.getUserId())
                                          .orElseThrow(() -> new NotFoundException("User not found")));
        }
        if(productDto.getSupplierId() != null) {
            product.setSupplier(supplierRepository.findById(productDto.getSupplierId())
                                            .orElse(null));
        }
        return product;
    }
}
