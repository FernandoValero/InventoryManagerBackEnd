package ar.com.manager.inventory.mapper;

import ar.com.manager.inventory.dto.ProductDto;
import ar.com.manager.inventory.dto.SupplierDto;
import ar.com.manager.inventory.entity.Product;
import ar.com.manager.inventory.entity.Supplier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SupplierMapper {

    ProductMapper productMapper;
    public SupplierMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public SupplierDto toDto(Supplier supplier) {
        SupplierDto supplierDto = new SupplierDto();
        List<ProductDto> products = new ArrayList<>();
        supplierDto.setId(supplier.getId());
        supplierDto.setFirstName(supplier.getFirstName());
        supplierDto.setLastName(supplier.getLastName());
        supplierDto.setPhoneNumber(supplier.getPhoneNumber());
        supplierDto.setEmail(supplier.getEmail());
        supplierDto.setCompany(supplier.getCompany());
        supplierDto.setProducts(products);
        if(supplier.getProducts() != null ) {
            for (Product product : supplier.getProducts()) {
                supplierDto.addProduct(productMapper.toDto(product));
            }
        }
        supplierDto.setDeleted(supplier.getDeleted());
        return supplierDto;
    }
    public Supplier toEntity(SupplierDto supplierDto) {
        Supplier supplier = new Supplier();
        supplier.setFirstName(supplierDto.getFirstName());
        supplier.setLastName(supplierDto.getLastName());
        supplier.setPhoneNumber(supplierDto.getPhoneNumber());
        supplier.setEmail(supplierDto.getEmail());
        supplier.setCompany(supplierDto.getCompany());
        return supplier;
    }
}
