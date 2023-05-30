package com.escahe.bank.service.implementation;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.escahe.bank.exception.client.ClientDBException;
import com.escahe.bank.exception.client.ClientDBException.ClientDBExceptionCause;
import com.escahe.bank.model.Client;
import com.escahe.bank.repository.ClientRepository;
import com.escahe.bank.service.IClientsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientsService{

    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final ClientRepository clientRepository;

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(Long id) {
        logger.info("Solicitud de busqueda de cliente por id " + id);
        Optional<Client> clientInDB = clientRepository.findById(id);
        if(clientInDB.isPresent()){
            logger.info("Cliente encontrado");
            return clientInDB.get();
        }
        logger.warn("El cliente buscado no existe");
        throw new ClientDBException(ClientDBExceptionCause.NOT_FOUND);
    }

    @Override
    public Client saveClient(Client client) { 
        logger.info("Solicitud de creacion de cliente " + client.toString());
        Optional<Client> clientInDb = clientRepository.findByDni(client.getDni());
        if(clientInDb.isPresent()){
            logger.warn("El cliente a crear tiene una identificacion en uso por otro cliente");
            throw new ClientDBException(ClientDBExceptionCause.ALREADY_EXISTS);
        }
        logger.info("Cliente creado exitosamente");
        return clientRepository.save(client);
    }

    @Override
    public Client partialClientUpdate(Client client, Long id) {
        Optional<Client> clientInDB = clientRepository.findById(id);
        logger.info("Solicitud de actualizacion parcial de cliente " + clientInDB.toString());
        if(client.getDni() != null && !client.getDni().equals(clientInDB.get().getDni()) && clientRepository.findByDni(client.getDni()).isPresent()){
            logger.warn("Intento actualizar la identificación por una de otro cliente ");
            throw new ClientDBException(ClientDBExceptionCause.DNI_ALREADY_IN_USE);
        }

        if (clientInDB.isPresent()) {
            if(!isAnyNullField(client)){
                logger.warn("Intento de actualización total en ruta de actualización parcial");
                throw new ClientDBException(ClientDBExceptionCause.PARTIAL_UPDATE_REQUIRED);
            }
            Client updatedClient = updateFields(clientInDB.get(), client);
            logger.info("Cliente actualizado correctamente " + updatedClient.toString());
            return clientRepository.save(updatedClient);
        }
        logger.warn("El cliente a actualizar no existe");
        throw new ClientDBException(ClientDBExceptionCause.NOT_FOUND);
    }

    @Override
    public Client fullClientUpdate(Client client, Long id) {
        Optional<Client> clientInDB = clientRepository.findById(id);
        logger.info("Solicitud de actualizacion total de cliente " + client.toString());
        if (clientInDB.isPresent()) {
            if(isAnyNullField(client)){
                logger.warn("Intento de actualización parcial en ruta de actualización total");
                throw new ClientDBException(ClientDBExceptionCause.FULL_UPDATE_REQUIRED);
            }
            
            if(!client.getDni().equals(clientInDB.get().getDni()) && clientRepository.findByDni(client.getDni()).isPresent()){
                logger.warn("Intento actualizar la identificación por una de otro cliente ");
                throw new ClientDBException(ClientDBExceptionCause.DNI_ALREADY_IN_USE);
            }
            client.setId(clientInDB.get().getId());
            logger.info("Cliente actualizado correctamente " + client.toString());
            return clientRepository.save(client);
        }
        logger.warn("El cliente a actualizar no existe");
        throw new ClientDBException(ClientDBExceptionCause.NOT_FOUND);
    }

    @Override
    public Client deleteClient(Long id) {
        logger.info("Solicitud de eliminacion de cliente con id " + id);
        Optional<Client> clientInDB = clientRepository.findById(id);
        if(clientInDB.isPresent()){
            clientRepository.deleteById(id);
            logger.info("Cliente eliminado " + clientInDB.toString());
            return clientInDB.get();
        }
        logger.warn("El cliente a eliminar no existe");
        throw new ClientDBException(ClientDBExceptionCause.NOT_FOUND);
    }

    private static Client updateFields(Client clientToBeUpdated, Client clientWithUpdatedFields) {
        String address = clientWithUpdatedFields.getAddress();
        Integer age = clientWithUpdatedFields.getAge();
        String dni = clientWithUpdatedFields.getDni();
        String genre = clientWithUpdatedFields.getGenre();
        String name = clientWithUpdatedFields.getName();
        String password = clientWithUpdatedFields.getPassword();
        String phone = clientWithUpdatedFields.getphoneNumber();
        Boolean status = clientWithUpdatedFields.getStatus();

        if(address != null){clientToBeUpdated.setAddress(address);}
        if(age != null){clientToBeUpdated.setAge(age);}
        if(dni != null){clientToBeUpdated.setDni(dni);}
        if(genre != null){clientToBeUpdated.setGenre(genre);}
        if(name != null){clientToBeUpdated.setName(name);}
        if(password != null){clientToBeUpdated.setPassword(password);}
        if(phone != null){clientToBeUpdated.setphoneNumber(phone);}
        if(status != null){clientToBeUpdated.setStatus(status);}

        return clientToBeUpdated;

    }

    private static boolean isAnyNullField(Client clientWithUpdatedFields) {
        String address = clientWithUpdatedFields.getAddress();
        Integer age = clientWithUpdatedFields.getAge();
        String dni = clientWithUpdatedFields.getDni();
        String genre = clientWithUpdatedFields.getGenre();
        String name = clientWithUpdatedFields.getName();
        String password = clientWithUpdatedFields.getPassword();
        String phone = clientWithUpdatedFields.getphoneNumber();
        Boolean status = clientWithUpdatedFields.getStatus();
        return address == null || age == null || dni == null || genre == null || name == null || password == null || phone == null || status == null;
    }
    
}
