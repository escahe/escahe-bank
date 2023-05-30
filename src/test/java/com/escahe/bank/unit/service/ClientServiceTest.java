package com.escahe.bank.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.escahe.bank.exception.client.ClientDBException;
import com.escahe.bank.model.Client;
import com.escahe.bank.repository.ClientRepository;
import com.escahe.bank.service.IClientsService;
import com.escahe.bank.service.implementation.ClientServiceImpl;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;
    private IClientsService clientService;

    @BeforeEach
    void setUp(){
        clientService = new ClientServiceImpl(clientRepository);
    }

    @Test
    public void canGetAllClients() {
        clientService.getAllClients();
        verify(clientRepository).findAll();
    }
    
    @Test
    public void canAddNewClient() {
        Client client = new Client(
            1L,
            "Homero Simpson",
            "masculino",
            40,
            "1111111111",
            "Avenida siempre viva 123",
            "3335555677",
            "flanders",
            true
        );

        clientService.saveClient(client);

        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);
        var stringCapture = ArgumentCaptor.forClass(String.class);
        verify(clientRepository).findByDni(stringCapture.capture());
        assertEquals(stringCapture.getValue(), client.getDni());
        verify(clientRepository).save(clientArgumentCaptor.capture());
        assertEquals(clientArgumentCaptor.getValue(), client);
    }
    @Test
    public void canNotAddClientWhenDniIsTaken() {
        Client client = new Client(
            1L,
            "Homero Simpson",
            "masculino",
            40,
            "1111111111",
            "Avenida siempre viva 123",
            "3335555677",
            "flanders",
            true
        );
        Client clientInDB = new Client();
        clientInDB.setDni(client.getDni());
        when(clientRepository.findByDni(client.getDni())).thenReturn(Optional.of(clientInDB));

        assertThrows(ClientDBException.class, ()-> clientService.saveClient(client));

        verify(clientRepository, never()).save(any());

    }
}
