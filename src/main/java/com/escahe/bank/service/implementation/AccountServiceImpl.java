package com.escahe.bank.service.implementation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.escahe.bank.exception.account.AccountDBException;
import com.escahe.bank.exception.account.AccountDBException.AccountDBExceptionCause;
import com.escahe.bank.exception.client.ClientDBException;
import com.escahe.bank.exception.client.ClientDBException.ClientDBExceptionCause;
import com.escahe.bank.model.Account;
import com.escahe.bank.repository.AccountRepository;
import com.escahe.bank.repository.ClientRepository;
import com.escahe.bank.service.IAccountsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountsService{
    
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @Override
    public List<Account> getAllAccounts() {
        logger.info("Solicitud de obtener todas las cuentas");
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(Long id) {
        logger.info("Solicitud de obtener cuenta con id " + id);
        return accountRepository.findById(id)
        .orElseThrow(()-> new AccountDBException(AccountDBExceptionCause.NOT_FOUND));
    }

    @Override
    public Account saveAccount(Account account) {
        logger.info("Solicitud de creacion de cuenta " + account.toString());
        if(!clientRepository.existsById(account.getClient().getId())){
            logger.warn("el cliente a asignar cuenta no existe");
            throw new ClientDBException(ClientDBExceptionCause.NOT_FOUND);
        }
        if(accountRepository.existsByNumber(account.getNumber())){
            logger.warn("Ya existe una cuenta con el numero " + account.getNumber());
            throw new AccountDBException(AccountDBExceptionCause.ACCOUNT_NUMBER_ALREADY_IN_USE);
        }
        logger.info("Creacion exitosa de cuenta " + account.toString());
        return accountRepository.save(account);
    }

    @Override
    public Account partialUpdateAccount(Account account, Long id) {
        Account accountInDB = accountRepository.findById(id)
            .orElseThrow(() -> new AccountDBException(AccountDBExceptionCause.NOT_FOUND));
        logger.info("Solicitud de actualizacion parcial de cuenta " + accountInDB.toString());
        if(
            account.getNumber() != null &&
            !account.getNumber().equals(accountInDB.getNumber()) && 
            accountRepository.existsByNumber(account.getNumber())
        ) {
            logger.warn("Intento de actualizar numero de cuenta a uno en uso");
            throw new AccountDBException(AccountDBExceptionCause.ACCOUNT_NUMBER_ALREADY_IN_USE);
        }
        Long clientId = account.getClient().getId();
        if (clientId != null && clientId != accountInDB.getClient().getId())
            accountInDB.setClient(
                clientRepository.findById(account.getClient().getId())
                    .orElseThrow(() -> new ClientDBException(ClientDBExceptionCause.NOT_FOUND))
        );
        if(account.getNumber() != null) accountInDB.setNumber(account.getNumber());
        if(account.getBalance() != null) accountInDB.setBalance(account.getBalance());
        if(account.getStatus() != null) accountInDB.setStatus(account.getStatus());
        if(account.getType() != null) accountInDB.setType(account.getType());

        logger.info("Actualizacion exitosa de cuenta " + accountInDB.toString());
        return accountRepository.save(accountInDB);
    }

    @Override
    public Account fullAccountUpdate(Account account, Long id) {
        Account accountInDB = accountRepository.findById(id)
            .orElseThrow(() -> new AccountDBException(AccountDBExceptionCause.NOT_FOUND));
        logger.info("Solicitud de actualizacion total de cuenta con id " + accountInDB.toString());
        if(
            account.getBalance() == null ||
            account.getStatus() == null ||
            account.getType() == null ||
            account.getNumber() == null ||
            account.getClient().getId() == null
        ) {
            logger.warn("Solicitud de actualizacion parcial en ruta de actualizacion total");
            throw new AccountDBException(AccountDBExceptionCause.FULL_UPDATE_REQUIRED);
        }
        if(
            !accountInDB.getNumber().equals(account.getNumber()) && 
            accountRepository.existsByNumber(account.getNumber())
        ) {
            logger.warn("Intento de actualizar numero de cuenta a uno en uso");
            throw new AccountDBException(AccountDBExceptionCause.ACCOUNT_NUMBER_ALREADY_IN_USE);
        }
        account.setClient(
            account.getClient().getId() != accountInDB.getClient().getId()?
            clientRepository.findById(account.getClient().getId())
                    .orElseThrow(() -> new ClientDBException(ClientDBExceptionCause.NOT_FOUND)):
            accountInDB.getClient()
        );
        account.setId(id);
        logger.info("Actualizacion exitosa de cuenta " + account.toString());
        return accountRepository.save(account);
    }

    @Override
    public Account deleteAccount(Long id) {
        logger.info("Solicitud de eliminacion de cuenta id " + id);
        Account accountToBeDeleted = accountRepository.findById(id)
            .orElseThrow(() -> new AccountDBException(AccountDBExceptionCause.NOT_FOUND));
        accountRepository.deleteById(id);
        logger.info("Eliminacion exitosa de cuenta " + accountToBeDeleted.toString());
        return accountToBeDeleted;
    }    
}
