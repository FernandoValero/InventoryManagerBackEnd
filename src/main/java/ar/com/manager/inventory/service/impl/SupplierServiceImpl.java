package ar.com.manager.inventory.service.impl;

import ar.com.manager.inventory.dto.SupplierDto;
import ar.com.manager.inventory.entity.Supplier;
import ar.com.manager.inventory.exception.NotFoundException;
import ar.com.manager.inventory.exception.ValidationException;
import ar.com.manager.inventory.mapper.SupplierMapper;
import ar.com.manager.inventory.repository.SupplierRepository;
import ar.com.manager.inventory.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {
    SupplierRepository supplierRepository;
    SupplierMapper supplierMapper;
    public SupplierServiceImpl(SupplierRepository supplierRepository, SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }
    @Override
    public SupplierDto addSupplier(SupplierDto supplierDto) {
        if(supplierRepository.existsByEmail(supplierDto.getEmail())) {
            throw new ValidationException("The supplier email already exists.");
        }
        if(supplierRepository.existsByPhoneNumber(supplierDto.getPhoneNumber())) {
            throw new ValidationException("The supplier phone number already exists.");
        }
        Supplier supplier = supplierMapper.toEntity(supplierDto);
        supplier.setDeleted(false);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return supplierMapper.toDto(savedSupplier);
    }

    @Override
    public SupplierDto updateSupplier(SupplierDto supplierDto, Integer id) {
        if(supplierDto == null){
            throw new ValidationException("The supplier cannot be null");
        }
        Supplier supplier = supplierRepository.findById(id).orElseThrow(()-> {
            String errorMessage = "The supplier with id " + id + " does not exist.";
            return new NotFoundException(errorMessage);
        });
        if(supplierRepository.existsByEmailAndIdNot(supplierDto.getEmail(), id)) {
            throw new ValidationException("The supplier email already exists.");
        }
        if(supplierRepository.existsByPhoneNumberAndIdNot(supplierDto.getPhoneNumber(), id)) {
            throw new ValidationException("The supplier phone number already exists.");
        }
        setSupplier(supplierDto,supplier);
        supplier = supplierRepository.save(supplier);
        return supplierMapper.toDto(supplier);
    }

    @Override
    public void deleteSupplier(Integer id) {
        Supplier supplier = supplierRepository.findById(id).orElse(null);
        if(supplier == null ){
            throw new NotFoundException("The supplier with id " + id + " does not exist.");
        }
        supplier.setDeleted(true);
        supplierRepository.save(supplier);
    }

    @Override
    public SupplierDto getSupplierById(Integer id) {
        Supplier supplier = supplierRepository.findById(id).orElse(null);
        if(supplier == null || supplier.getDeleted()){
            throw new NotFoundException("The supplier with id " + id + " does not exist.");
        }
        return supplierMapper.toDto(supplier);
    }

    @Override
    public List<SupplierDto> getAllSuppliers() {
        return supplierRepository.findByDeletedFalse()
                .stream()
                .map(supplierMapper::toDto)
                .collect(Collectors.toList());
    }

    private void setSupplier(SupplierDto modifiedSupplier, Supplier finalSupplier) {
        finalSupplier.setFirstName(modifiedSupplier.getFirstName());
        finalSupplier.setLastName(modifiedSupplier.getLastName());
        finalSupplier.setPhoneNumber(modifiedSupplier.getPhoneNumber());
        finalSupplier.setEmail(modifiedSupplier.getEmail());
        finalSupplier.setCompany(modifiedSupplier.getCompany());
    }
}
