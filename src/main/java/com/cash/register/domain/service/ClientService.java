package com.cash.register.domain.service;

import com.cash.register.domain.entity.Client;

import java.util.List;

public interface ClientService {

    Client addClient(Client client);

    Client updateClient(Long id, Client client);

    Client getClientById(Long id);

    List<Client> getClientList();

    List<Client> getClientFilter(String name, String cpf, String city, String uf);

    Client deleteClientById(Long id);
}
