package ar.com.manager.inventory.service;

import ar.com.manager.inventory.dto.ClientDto;

import java.util.List;

public interface ClientService {
    ClientDto addClient(ClientDto clientDto);
    ClientDto updateClient(ClientDto clientDto, Integer id);
    void deleteClient(Integer id);
    ClientDto getClientById(Integer id);
    List<ClientDto> getAllClients();

}
