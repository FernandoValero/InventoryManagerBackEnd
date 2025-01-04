package ar.com.manager.inventory.mapper;

import ar.com.manager.inventory.dto.SaleDetailDto;
import ar.com.manager.inventory.entity.SaleDetail;
import org.springframework.stereotype.Component;

@Component
public class SaleDetailMapper {
    ProductMapper productMapper;
    public SaleDetailMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public SaleDetailDto toDto(SaleDetail saleDetail) {
        SaleDetailDto saleDetailDto = new SaleDetailDto();
        saleDetailDto.setId(saleDetail.getId());
        saleDetailDto.setAmount(saleDetail.getAmount());
        saleDetailDto.setProduct(productMapper.toDto(saleDetail.getProduct()));
        return saleDetailDto;
    }

    public SaleDetail toEntity(SaleDetailDto saleDetailDto) {
        SaleDetail saleDetail = new SaleDetail();
        saleDetail.setAmount(saleDetailDto.getAmount());
        saleDetail.setProduct(productMapper.toEntity(saleDetailDto.getProduct()));
        return saleDetail;
    }
}
