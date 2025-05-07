package ar.com.manager.inventory.service.impl;

import ar.com.manager.inventory.dto.ClientDto;
import ar.com.manager.inventory.entity.Client;
import ar.com.manager.inventory.entity.Sale;
import ar.com.manager.inventory.exception.NotFoundException;
import ar.com.manager.inventory.exception.ValidationException;
import ar.com.manager.inventory.mapper.ClientMapper;
import ar.com.manager.inventory.mapper.ProductMapper;
import ar.com.manager.inventory.repository.ClientRepository;
import ar.com.manager.inventory.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientDto addClient(ClientDto clientDto) throws ValidationException {
        if(clientRepository.existsByDni(clientDto.getDni())){
            throw new ValidationException("The client already exists");
        }
        Client client = clientMapper.toEntity(clientDto);
        client.setDeleted(false);
        List<Sale> sales = new ArrayList<>();
        client.setSales(sales);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toDto(savedClient);
    }

    @Override
    public ClientDto updateClient(ClientDto clientDto,Integer id) throws ValidationException {
        if(clientDto == null){
            throw new ValidationException("The client cannot be null");
        }
        Client client = clientRepository.findById(id).orElseThrow(() -> {
            String errorMessage = "The client with id " + id + " does not exist.";
            return new NotFoundException(errorMessage);
        });
        if (clientRepository.existsByDniAndIdNot(clientDto.getDni(), id)) {
            throw new ValidationException("The client DNI already exists");
        }
        setClient(clientDto, client);
        client = clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    @Override
    public void deleteClient(Integer id) throws NotFoundException {
        Client client = clientRepository.findById(id).orElse(null);
        if(client == null){
            throw new NotFoundException("The client does not exist");
        }
        client.setDeleted(true);
        clientRepository.save(client);
    }

    @Override
    public ClientDto getClientById(Integer id) throws NotFoundException {
        Client client = clientRepository.findById(id).orElse(null);
        if(client == null || client.getDeleted()){
            throw new NotFoundException("The client does not exist");
        }
        return clientMapper.toDto(client);
    }

    @Override
    public List<ClientDto> getAllClients() {
        return clientRepository.findByDeletedFalse()
                .stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    private void setClient (ClientDto modifiedClient, Client finalClient) {
        finalClient.setFirstName(modifiedClient.getFirstName());
        finalClient.setLastName(modifiedClient.getLastName());
        finalClient.setDni(modifiedClient.getDni());
    }
}
