package ar.com.manager.inventory.service;

import ar.com.manager.inventory.dto.SaleDto;

import java.util.List;

public interface SaleService {
    SaleDto addSale(SaleDto saleDto);
    SaleDto updateSale(SaleDto saleDto, Integer id);
    void deleteSale(Integer id);
    SaleDto getSaleById(Integer id);
    List<SaleDto> getAllSales();
}
