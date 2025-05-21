package ar.com.manager.inventory.mapper;

import ar.com.manager.inventory.dto.SaleDetailDto;
import ar.com.manager.inventory.entity.SaleDetail;
import ar.com.manager.inventory.exception.NotFoundException;
import ar.com.manager.inventory.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class SaleDetailMapper {
    ProductMapper productMapper;
    ProductRepository productRepository;
    public SaleDetailMapper(ProductMapper productMapper, ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    public SaleDetailDto toDto(SaleDetail saleDetail) {
        SaleDetailDto saleDetailDto = new SaleDetailDto();
        saleDetailDto.setId(saleDetail.getId());
        saleDetailDto.setAmount(saleDetail.getAmount());
        saleDetailDto.setProduct(productMapper.toDto(saleDetail.getProduct()));
        saleDetailDto.setDeleted(saleDetail.getDeleted());
        return saleDetailDto;
    }

    public SaleDetail toEntity(SaleDetailDto saleDetailDto) {
        SaleDetail saleDetail = new SaleDetail();
        saleDetail.setAmount(saleDetailDto.getAmount());
        if(saleDetailDto.getProduct()!=null && saleDetailDto.getProduct().getId()!=null){
            saleDetail.setProduct(productRepository.findById(saleDetailDto.getProduct().getId())
                    .orElseThrow(() -> new NotFoundException("Product not found")));
        }
        return saleDetail;
    }
}
