package ar.com.manager.inventory.service.impl;

import ar.com.manager.inventory.dto.SaleDetailDto;
import ar.com.manager.inventory.mapper.SaleDetailMapper;
import ar.com.manager.inventory.repository.SaleDetailRepository;
import ar.com.manager.inventory.service.SaleDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleDetailServiceImpl implements SaleDetailService {
    private final SaleDetailRepository saleDetailRepository;
    private final SaleDetailMapper saleDetailMapper;
    public SaleDetailServiceImpl(SaleDetailRepository saleDetailRepository, SaleDetailMapper saleDetailMapper) {
        this.saleDetailRepository = saleDetailRepository;
        this.saleDetailMapper = saleDetailMapper;
    }

    //Verificar si estos metodos son necesarios

    @Override
    public SaleDetailDto addSaleDetail(SaleDetailDto saleDetailDto) {
        return null;
    }

    @Override
    public SaleDetailDto getSaleDetailById(Integer id) {
        return null;
    }

    @Override
    public List<SaleDetailDto> getAllSaleDetails() {
        return List.of();
    }
}
