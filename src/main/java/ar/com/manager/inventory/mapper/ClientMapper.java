package ar.com.manager.inventory.mapper;

import ar.com.manager.inventory.dto.ClientDto;
import ar.com.manager.inventory.dto.SaleDto;
import ar.com.manager.inventory.entity.Client;
import ar.com.manager.inventory.entity.Sale;
import ar.com.manager.inventory.entity.User;
import ar.com.manager.inventory.repository.ClientRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClientMapper {

    SaleMapper saleMapper;
    public ClientMapper(SaleMapper saleMapper) {
        this.saleMapper = saleMapper;
    }

    public ClientDto toDto(Client client) {
        ClientDto clientDto = new ClientDto();
        List<SaleDto> sales = new ArrayList<>();
        clientDto.setId(client.getId());
        clientDto.setFirstName(client.getFirstName());
        clientDto.setLastName(client.getLastName());
        clientDto.setDni( client.getDni());
        clientDto.setSales(sales);
        if(client.getSales() != null ) {
            for (Sale sale : client.getSales()) {
                clientDto.addSale(saleMapper.toDto(sale));
            }
        }
        clientDto.setDeleted(client.getDeleted());
        return clientDto;
    }

    public Client toEntity(ClientDto clientDto) {
        Client client = new Client();
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setDni(clientDto.getDni());
        return client;
    }

}
