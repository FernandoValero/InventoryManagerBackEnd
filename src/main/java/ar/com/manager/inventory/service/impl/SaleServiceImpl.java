package ar.com.manager.inventory.service.impl;

import ar.com.manager.inventory.dto.SaleDetailDto;
import ar.com.manager.inventory.dto.SaleDto;
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
        System.out.println("Muestra el Println");
        saleDto.getSaleDetail().forEach(saleDetailDto -> {
            if (saleDetailDto.getAmount() <= 0) {
                throw new ValidationException("The amount in sale details must be greater than 0");
            }
            if (saleDetailDto.getProduct().getId() == null || !productRepository.existsById(saleDetailDto.getProduct().getId())) {
                throw new ValidationException("The product in sale details does not exist");
            }
        });
        Sale sale = saleMapper.toEntity(saleDto);
        System.out.println("Se convirtio la entidad de DTO a Entity");
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
        return saleMapper.toDto(sale);
    }

    //Verificar uso
    @Override
    public SaleDto updateSale(SaleDto saleDto, Integer id) {
        return null;
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
    public SaleDto getSaleById(Integer id) throws NotFoundException {
        Sale sale = saleRepository.findById(id).orElse(null);
        if (sale == null || sale.getDeleted()) {
            throw new NotFoundException("The sale with id " + id + " does not exist.");
        }
        return saleMapper.toDto(sale);
    }

    @Override
    public List<SaleDto> getAllSales() {
        return saleRepository.findByDeletedFalse()
                .stream()
                .map(saleMapper::toDto)
                .collect(Collectors.toList());
    }

    private Double calculateTotal(List<SaleDetail>  saleDetails){
        Double total = 0.0;
        for (SaleDetail detail : saleDetails) {
            total += detail.getAmount() * detail.getProduct().getPrice();
        }
        return total;
    }
}
