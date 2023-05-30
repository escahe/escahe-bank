package com.escahe.bank.service;

import java.util.List;

import com.escahe.bank.model.Client;

public interface IClientsService {
    List<Client> getAllClients();
    Client getClientById(Long id);
    Client saveClient(Client client);
    Client partialClientUpdate(Client client, Long id);
    Client fullClientUpdate(Client client, Long id);
    Client deleteClient(Long id);
}
