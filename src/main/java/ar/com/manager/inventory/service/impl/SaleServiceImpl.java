package ar.com.manager.inventory.service.impl;

import ar.com.manager.inventory.dto.SaleDetailDto;
import ar.com.manager.inventory.dto.SaleDto;
import ar.com.manager.inventory.entity.Product;
import ar.com.manager.inventory.entity.Sale;
import ar.com.manager.inventory.entity.SaleDetail;
import ar.com.manager.inventory.exception.NotFoundException;
import ar.com.manager.inventory.exception.ValidationException;
import ar.com.manager.inventory.mapper.SaleDetailMapper;
import ar.com.manager.inventory.mapper.SaleMapper;
import ar.com.manager.inventory.repository.ProductRepository;
import ar.com.manager.inventory.repository.SaleDetailRepository;
import ar.com.manager.inventory.repository.SaleRepository;
import ar.com.manager.inventory.service.SaleService;
import ar.com.manager.inventory.util.Util;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleDetailMapper saleDetailMapper;
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final SaleDetailRepository saleDetailRepository;
    private final SaleMapper saleMapper;

    public SaleServiceImpl(SaleDetailMapper saleDetailMapper, SaleRepository saleRepository, ProductRepository productRepository,SaleDetailRepository saleDetailRepository , SaleMapper saleMapper) {
        this.saleDetailMapper = saleDetailMapper;
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.saleDetailRepository = saleDetailRepository;
        this.saleMapper = saleMapper;
    }

    @Override
    public SaleDto addSale(SaleDto saleDto) throws ValidationException {
        if (saleDto == null || saleDto.getSaleDetail() == null || saleDto.getSaleDetail().isEmpty()) {
            throw new ValidationException("The sale or its details cannot be null or empty");
        }
        if(saleDto.getUserId() == null){
            throw new ValidationException("The user id is required.");
        }
        saleDto.getSaleDetail().forEach(saleDetailDto -> {
            if (saleDetailDto.getAmount() <= 0) {
                throw new ValidationException("The amount in sale details must be greater than 0");
            }
            Product product = productRepository.findById(saleDetailDto.getProduct().getId()).orElseThrow(() -> {
                String errorMessage = "The product with id " + saleDetailDto.getProduct().getId() + " does not exist.";
                return new NotFoundException(errorMessage);
            });
            if (product.getStock() < saleDetailDto.getAmount()) {
                throw new ValidationException("The product in sale details does not have enough stock");
            }
        });
        Sale sale = saleMapper.toEntity(saleDto);
        sale.setDeleted(false);

        List<SaleDetail> saleDetails = new ArrayList<>();
        for (SaleDetailDto saleDetailDto : saleDto.getSaleDetail()) {
            SaleDetail saleDetail = saleDetailMapper.toEntity(saleDetailDto);
            saleDetail.setDeleted(false);
            saleDetails.add(saleDetail);
        }
        sale.setSaleDetails(saleDetails);
        LocalDateTime now = LocalDateTime.now();
        sale.setTotalPrice(calculateTotal(saleDetails));
        sale.setSaleDate(now);

        sale = saleRepository.save(sale);
        saleDetails.forEach(this::updateProductStock);
        return saleMapper.toDto(sale);
    }

    @Override
    public void deleteSale(Integer id) throws ValidationException {
        Sale sale = saleRepository.findById(id).orElse(null);
        if (sale == null) {
            throw new ValidationException("The sale with id " + id + " does not exist.");
        }
        sale.setDeleted(true);
        saleRepository.save(sale);
    }

    @Override
    public List<SaleDto> getAllSales() {
        return saleRepository.findByDeletedFalse()
                .stream()
                .map(saleMapper::toDto)
                .collect(Collectors.toList());
    }

    //Filters
    @Override
    public SaleDto getSaleById(Integer id) throws NotFoundException {
        Sale sale = saleRepository.findById(id).orElse(null);
        if (sale == null || sale.getDeleted()) {
            throw new NotFoundException("The sale with id " + id + " does not exist.");
        }
        return saleMapper.toDto(sale);
    }

    @Override
    public List<SaleDto> findBySaleDateBetween(String startDate, String endDate) {
        LocalDateTime start = Util.stringToLocalDateTime(startDate);
        LocalDateTime end = Util.stringToLocalDateTime(endDate);

        if (start.isAfter(end)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }

        return saleRepository.findBySaleDateBetweenAndDeletedFalse(start, end)
                .stream()
                .map(saleMapper::toDto)
                .toList();
    }


    @Override
    public List<SaleDto> findByClientId(Integer clientId) {
        return saleRepository.findByClientIdAndDeletedFalse(clientId)
                .stream()
                .map(saleMapper::toDto)
                .toList();
    }

    @Override
    public List<SaleDto> findByUserId(Integer userId) {
        return saleRepository.findByUserIdAndDeletedFalse(userId)
                .stream()
                .map(saleMapper::toDto)
                .toList();
    }

    @Override
    public List<SaleDto> findBySaleDateMonth(int monthNumber) {
        if (monthNumber < 1 || monthNumber > 12) {
            throw new IllegalArgumentException("Invalid month number. The month number must be between 1 and 12");
        }
        return saleRepository.findBySaleDateMonthAndDeletedFalse(monthNumber)
                .stream()
                .map(saleMapper::toDto)
                .toList();
    }

    @Override
    public List<SaleDto> findBySaleDateYear(int year) {
        if (year < 2020 || year > 9999) {
            throw new IllegalArgumentException("The year must be in a valid range (2020-9999)");
        }
        return saleRepository.findBySaleDateYearAndDeletedFalse(year)
                .stream()
                .map(saleMapper::toDto)
                .toList();
    }

    @Override
    public List<SaleDto> findByProductId(Integer productId) {
        return saleRepository.findByDeletedFalse()
                .stream()
                .filter(sale -> sale.getSaleDetails().stream()
                        .anyMatch(item -> item.getProduct().getId().equals(productId)))
                .map(saleMapper::toDto)
                .toList();
    }

    private Double calculateTotal(List<SaleDetail>  saleDetails){
        Double total = 0.0;
        for (SaleDetail detail : saleDetails) {
            total += detail.getAmount() * detail.getProduct().getPrice();
        }
        return total;
    }

    private void updateProductStock(SaleDetail saleDetail){
        Product product = saleDetail.getProduct();
        Integer amount = saleDetail.getAmount();
        product.setStock(product.getStock() - amount);
        productRepository.save(product);
    }
}