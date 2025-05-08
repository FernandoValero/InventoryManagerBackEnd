package ar.com.manager.inventory.mapper;

import ar.com.manager.inventory.dto.SaleDetailDto;
import ar.com.manager.inventory.dto.SaleDto;
import ar.com.manager.inventory.entity.Sale;
import ar.com.manager.inventory.entity.SaleDetail;
import ar.com.manager.inventory.exception.NotFoundException;
import ar.com.manager.inventory.repository.ClientRepository;
import ar.com.manager.inventory.repository.UserRepository;
import ar.com.manager.inventory.util.Util;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SaleMapper {

    SaleDetailMapper saleDetailMapper;
    ClientRepository clientRepository;
    UserRepository userRepository;
    SaleMapper(SaleDetailMapper saleDetailMapper, ClientRepository clientRepository, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.saleDetailMapper = saleDetailMapper;
    }

    public SaleDto toDto(Sale sale) {
        SaleDto saleDto = new SaleDto();
        List<SaleDetailDto> saleDetails = new ArrayList<>();
        saleDto.setId(sale.getId());
        saleDto.setSaleDate(Util.localDateTimeToString(sale.getSaleDate()));
        saleDto.setTotalPrice(sale.getTotalPrice());
        saleDto.setUserId(sale.getUser().getId());
        if(sale.getClient() != null){
            saleDto.setClientId(sale.getUser().getId());
        }
        saleDto.setSaleDetail(saleDetails);
        for(SaleDetail saleDetail : sale.getSaleDetails()) {
            saleDto.addSaleDetail(saleDetailMapper.toDto(saleDetail));
        }
        return saleDto;
}

    public Sale toEntity(SaleDto saleDto) {
        Sale sale = new Sale();
        if(saleDto.getUserId()!=null){
            sale.setUser(userRepository.findById(saleDto.getUserId())
                                        .orElseThrow(() -> new NotFoundException("User not found")));
        }
        if(saleDto.getClientId()!=null){
            sale.setClient(clientRepository.findById(saleDto.getClientId())
                                            .orElse(null));
        }
        return sale;
    }

}
