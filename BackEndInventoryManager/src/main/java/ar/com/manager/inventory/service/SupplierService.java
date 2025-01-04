package ar.com.manager.inventory.service;

import ar.com.manager.inventory.dto.SupplierDto;

import java.util.List;

public interface SupplierService {
    SupplierDto addSupplier(SupplierDto supplierDto);
    SupplierDto updateSupplier(SupplierDto supplierDto, Integer id);
    void deleteSupplier(Integer id);
    SupplierDto getSupplierById(Integer id);
    List<SupplierDto> getAllSuppliers();
}
