package ar.com.manager.inventory.mapper;

import ar.com.manager.inventory.dto.SaleDto;
import ar.com.manager.inventory.entity.Sale;
import ar.com.manager.inventory.entity.SaleDetail;
import ar.com.manager.inventory.util.Util;
import org.springframework.stereotype.Component;

@Component
public class SaleMapper {

    SaleDetailMapper saleDetailMapper;
    SaleMapper(SaleDetailMapper saleDetailMapper) {
        this.saleDetailMapper = saleDetailMapper;
    }

    public SaleDto toDto(Sale sale) {
        SaleDto saleDto = new SaleDto();
        saleDto.setId(sale.getId());
        saleDto.setSaleDate(Util.localDateTimeToString(sale.getSaleDate()));
        saleDto.setTotalPrice(sale.getTotalPrice());
        saleDto.setUserId(sale.getUser().getId());
        saleDto.setClientId(sale.getClient().getId());
        for(SaleDetail saleDetail : sale.getSaleDetails()) {
            saleDto.addSaleDetail(saleDetailMapper.toDto(saleDetail));
        }
        return saleDto;
    }

    public Sale toEntity(SaleDto saleDto) {
        Sale sale = new Sale();
        sale.setSaleDate(Util.stringToLocalDateTime(saleDto.getSaleDate()));
        sale.setTotalPrice(saleDto.getTotalPrice());
        return sale;
    }

}
