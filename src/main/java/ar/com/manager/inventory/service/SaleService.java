package ar.com.manager.inventory.service;

import ar.com.manager.inventory.dto.SaleDto;

import java.util.List;

public interface SaleService {
    SaleDto addSale(SaleDto saleDto);
    void deleteSale(Integer id);
    SaleDto getSaleById(Integer id);
    List<SaleDto> getAllSales();

    //Filters
    //List<SaleDto> findBySaleDateBetween(String startDate, String endDate);
    List<SaleDto> findByClientId(Integer clientId);
    List<SaleDto> findByUserId(Integer userId);
    List<SaleDto> findBySaleDateMonth(int monthNumber);
    List<SaleDto> findBySaleDateYear(int yearNumber);
    List<SaleDto> findByProductId(Integer productId);
}
