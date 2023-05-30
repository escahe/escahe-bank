package com.escahe.bank.service.implementation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.escahe.bank.exception.account.AccountDBException;
import com.escahe.bank.exception.account.AccountDBException.AccountDBExceptionCause;
import com.escahe.bank.exception.client.ClientDBException;
import com.escahe.bank.exception.client.ClientDBException.ClientDBExceptionCause;
import com.escahe.bank.exception.movement.MovementDBException;
import com.escahe.bank.exception.movement.MovementDBException.MovementDBExceptionCause;
import com.escahe.bank.model.Account;
import com.escahe.bank.model.Movement;
import com.escahe.bank.repository.AccountRepository;
import com.escahe.bank.repository.ClientRepository;
import com.escahe.bank.repository.MovementRepository;
import com.escahe.bank.service.IMovementsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements IMovementsService{

    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @Override
    public Movement deposit(String accountNumber, BigDecimal amount) {
        amount = amount.abs();
        logger.info("Solicitud de deposito de " + amount.toString() + " en cuenta numero " + accountNumber);
        Account account =  accountRepository.findByNumber(accountNumber)
            .orElseThrow(() -> new AccountDBException(AccountDBExceptionCause.NOT_FOUND));
        
        BigDecimal newBalance = account.getBalance().add(amount);
        Movement movement = new Movement(
            LocalDateTime.now(),
            amount,
            newBalance,
            account
        );
        account.setBalance(newBalance);
        accountRepository.save(account);
        movementRepository.save(movement);
        logger.info("Deposito exitoso " + movement.toString());
        return movement;
    }

    @Override
    public Movement withdraw(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByNumber(accountNumber)
            .orElseThrow(() -> new AccountDBException(AccountDBExceptionCause.NOT_FOUND));
        amount = amount.abs().negate();
        logger.info("Solicitud de retiro de " + amount.toString() + " cuenta numero " + accountNumber);
        BigDecimal limit = BigDecimal.valueOf(-1000);
        if(amount.compareTo(limit) < 0){
            logger.warn("El retiro supera el límite");
            throw new MovementDBException(MovementDBExceptionCause.EXCEEDS_WITHDRAWAL_LIMIT);
        }

        BigDecimal totalWithdrawnToday = movementRepository.totalWithdrawnTodayByAccount(
            accountNumber
        );
        if(totalWithdrawnToday.equals(limit)){
            logger.warn("La cuenta ha alcanzado el límite diario");
            throw new MovementDBException(MovementDBExceptionCause.DAILY_LIMIT_REACHED);
        }
        if(totalWithdrawnToday.add(amount).compareTo(limit) < 0){
            logger.warn("El retiro supera el limite");
            throw new MovementDBException(MovementDBExceptionCause.EXCEEDS_WITHDRAWAL_LIMIT);
        }
        BigDecimal newBalance = account.getBalance().add(amount);
        if(newBalance.compareTo(BigDecimal.ZERO) < 0){
            logger.warn("Fondos insuficientes");
            throw new MovementDBException(MovementDBExceptionCause.INSUFFICIENT_FUNDS);
        }
        
        Movement movement = new Movement(
            LocalDateTime.now(),
            amount,
            newBalance,
            account
        );
        account.setBalance(newBalance);
        accountRepository.save(account);
        movementRepository.save(movement);
        logger.info("Retiro exitoso " + movement.toString());
        return movement;
    }

    @Override
    public List<Movement> getAllMovementsByClientIdBetweenDates(Long clientId, LocalDate startDate, LocalDate endDate) {
        logger.info("Solicitud de reporte de movimientos de cliente id " + clientId + " entre " + startDate + " y " + endDate);
        if(!clientRepository.existsById(clientId)){
            logger.warn("Cliente no existe");
            throw new ClientDBException(ClientDBExceptionCause.NOT_FOUND);
        }
        if(startDate.compareTo(endDate) > 0){
            logger.warn("La fecha desde es posterior a la fecha hasta");
            throw new MovementDBException(MovementDBExceptionCause.INVALID_DATE_RANGE);
        }
        logger.info("Reporte de movimientos generado exitosamente");
        return movementRepository.findByAccountClientIdAndDateBetween(clientId, startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
    }
    
}

