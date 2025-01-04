package ar.com.manager.inventory.service;

import ar.com.manager.inventory.dto.SaleDetailDto;

import java.util.List;

public interface SaleDetailService {
    SaleDetailDto addSaleDetail(SaleDetailDto saleDetailDto);
    SaleDetailDto getSaleDetailById(Integer id);
    List<SaleDetailDto> getAllSaleDetails();
}
