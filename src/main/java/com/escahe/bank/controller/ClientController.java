package com.escahe.bank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.escahe.bank.model.Client;
import com.escahe.bank.service.IClientsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
class ClientController {

    private final IClientsService clientsService;

    @GetMapping
    public ResponseEntity<List<Client>> getAll() {
        List<Client> clients = clientsService.getAllClients();

        if (clients.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable("id") Long id) {
        Client clientResult = clientsService.getClientById(id);
        return ResponseEntity.ok(clientResult);
    }

    @PostMapping
    public ResponseEntity<Client> create(@RequestBody Client client) {
        Client savedClient = clientsService.saveClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable("id") Long id, @RequestBody Client client) {
        Client clientResult = clientsService.fullClientUpdate(client, id);
        return ResponseEntity.ok(clientResult);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Client> partialUpdate(@PathVariable("id") Long id, @RequestBody Client client) {
        Client clientResult = clientsService.partialClientUpdate(client, id);
        return ResponseEntity.ok(clientResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client> delete(@PathVariable("id") Long id) {
        Client clientResult = clientsService.deleteClient(id);
        return ResponseEntity.ok(clientResult);
    }
}
