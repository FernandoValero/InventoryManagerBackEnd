package ar.com.manager.inventory.service.impl;

import ar.com.manager.inventory.dto.ProductDto;
import ar.com.manager.inventory.entity.Product;
import ar.com.manager.inventory.exception.NotFoundException;
import ar.com.manager.inventory.exception.ValidationException;
import ar.com.manager.inventory.mapper.ProductMapper;
import ar.com.manager.inventory.repository.ProductRepository;
import ar.com.manager.inventory.repository.SupplierRepository;
import ar.com.manager.inventory.repository.UserRepository;
import ar.com.manager.inventory.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, UserRepository userRepository, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.userRepository = userRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) throws ValidationException {
        if (productRepository.existsByBarCode(productDto.getBarCode())) {
            throw new ValidationException("The product barcode already exists.");
        }
        if (productRepository.existsByNumber(productDto.getNumber())) {
            throw new ValidationException("The product number already exists.");
        }
        if (productDto.getUserId() == null) {
            throw new ValidationException("The user id is required.");
        }
        if (productDto.getPrice() <= 0) {
            throw new IllegalArgumentException("The price must be greater than 0.");
        }
        Product product = productMapper.toEntity(productDto);
        product.setDeleted(false);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Integer id) throws NotFoundException, ValidationException {
        if(productDto == null){
            throw new ValidationException("The product cannot be null");
        }
        Product product = productRepository.findById(id).orElseThrow(() -> {
            String errorMessage = "The product with id " + id + " does not exist.";
            return new NotFoundException(errorMessage);
        });
        if (productRepository.existsByNumberAndIdNot(productDto.getNumber(), id)) {
            throw new ValidationException("The product number already exists");
        }
        if (productRepository.existsByBarCodeAndIdNot(productDto.getBarCode(), id)) {
            throw new ValidationException("The product barcode already exists");
        }
        setProduct(productDto, product);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Override
    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new NotFoundException("The product with id " + id + " does not exist.");
        }
        product.setDeleted(true);
        productRepository.save(product);
    }

    @Override
    public ProductDto getProductById(Integer id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null || product.getDeleted()) {
            throw new NotFoundException("The product with id " + id + " does not exist.");
        }
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findByDeletedFalse()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    private void setProduct (ProductDto modifiedProduct, Product finalProduct) {
        finalProduct.setName(modifiedProduct.getName());
        finalProduct.setStock(modifiedProduct.getStock());
        finalProduct.setBarCode(modifiedProduct.getBarCode());
        finalProduct.setPrice(modifiedProduct.getPrice());
        finalProduct.setDescription(modifiedProduct.getDescription());
        finalProduct.setCategory(modifiedProduct.getCategory());
        finalProduct.setImage(modifiedProduct.getImage());
        finalProduct.setUser(
                userRepository.findById(modifiedProduct.getUserId())
                        .orElseThrow(() -> new NotFoundException("User not found with ID: " + modifiedProduct.getUserId()))
        );

        finalProduct.setSupplier(
                supplierRepository.findById(modifiedProduct.getSupplierId())
                        .orElseThrow(() -> new NotFoundException("Supplier not found with ID: " + modifiedProduct.getSupplierId()))
        );
    }
}
