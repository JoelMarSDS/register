package com.cash.register.domain.service;

import com.cash.register.domain.entity.Client;

import java.util.List;

public interface ClientService {

    Client addClient(Client client, String name, String password);

    Client updateClient(Long id, Client client, String name, String password);

    Client getClientById(Long id, String name, String password);

    List<Client> getClientList(String name, String password);

    List<Client> getClientFilter(String nameParam, String cpf, String city, String uf, String name, String password);

    Client deleteClientById(Long id, String name, String password);
}
