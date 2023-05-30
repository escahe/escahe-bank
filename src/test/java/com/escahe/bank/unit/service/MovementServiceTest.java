package com.escahe.bank.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.escahe.bank.exception.account.AccountDBException;
import com.escahe.bank.exception.movement.MovementDBException;
import com.escahe.bank.model.Account;
import com.escahe.bank.model.Client;
import com.escahe.bank.model.Movement;
import com.escahe.bank.repository.AccountRepository;
import com.escahe.bank.repository.ClientRepository;
import com.escahe.bank.repository.MovementRepository;
import com.escahe.bank.service.IMovementsService;
import com.escahe.bank.service.implementation.MovementServiceImpl;

@DataJpaTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class MovementServiceTest {
    @Autowired
    private MovementRepository movementRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    private IMovementsService movementsService;
    Client client;
    Account account;

    @BeforeEach
    void setUp(){
        movementsService = new MovementServiceImpl(movementRepository, accountRepository, clientRepository);
        client = clientRepository.save(new Client(
            1L,
            "Homero Simpson",
            "masculino",
            40,
            "1111111111",
            "Avenida siempre viva 123",
            "3335555677",
            "flanders",
            true
        ));
        account = accountRepository.save(
            new Account(
            1L,
            "1234567890",
            "ahorro",
            BigDecimal.valueOf(500),
            true,
            client
        )); 
    }
    @Test
    public void depositOk() {

        BigDecimal depositAmount = BigDecimal.valueOf(1000);
        BigDecimal balanceAfterDeposit = account.getBalance().add(depositAmount);

        Movement movement = movementsService.deposit(account.getNumber(), depositAmount);

        assertEquals(balanceAfterDeposit, movement.getBalanceAfter());
        assertEquals(balanceAfterDeposit, accountRepository.findById(1L).get().getBalance());
    }

    @Test
    public void withdrawOk() {

        BigDecimal withdrawalAmount = BigDecimal.valueOf(499);
        BigDecimal balanceAfterWithdraw = account.getBalance().subtract(withdrawalAmount);

        Movement movement = movementsService.withdraw(account.getNumber(), withdrawalAmount);

        assertEquals(balanceAfterWithdraw, movement.getBalanceAfter());
        assertEquals(balanceAfterWithdraw, accountRepository.findById(1L).get().getBalance());
    }

    @Test
    public void attemptToWithdrawMoreThanAvailableBalance() {

        BigDecimal withdrawalAmount = BigDecimal.valueOf(1000);

        assertThrows(
            MovementDBException.class,
            ()-> movementsService.withdraw(account.getNumber(), withdrawalAmount)
        );
    }

    @Test
    public void attemptToWithdrawWhenDailyLimitReached() {

        account.setBalance(BigDecimal.valueOf(2000));
        accountRepository.save(account);
        BigDecimal withdrawalAmount = BigDecimal.valueOf(50);

        movementsService.withdraw(account.getNumber(), BigDecimal.valueOf(1000));

        assertThrows(
            MovementDBException.class,
            ()-> movementsService.withdraw(account.getNumber(), withdrawalAmount)
        );
    }

    @Test
    public void attemptToDepositToNonExistentAccount() {

        BigDecimal depositAmount = BigDecimal.valueOf(500);

        assertThrows(
            AccountDBException.class,
            ()-> movementsService.deposit("1233211231", depositAmount)
        );
    }

    @Test
    public void attemptToWithdrawFromNonExistentAccount() {

        BigDecimal withdrawalAmount = BigDecimal.valueOf(500);

        assertThrows(
            AccountDBException.class,
            ()-> movementsService.withdraw("1233211231", withdrawalAmount)
        );
    }

    @Test
    public void attemptToWithdrawDayAfterLimitReached() {
        BigDecimal thousand = BigDecimal.valueOf(1000);
        account.setBalance(BigDecimal.valueOf(2000));
        accountRepository.save(account);
        movementRepository.save(
            new Movement(
                LocalDateTime.now().minusDays(1),
                thousand.negate(),
                account.getBalance().subtract(thousand),
                account
            )
        );
        Movement movement = movementsService.withdraw(account.getNumber(), thousand);

        assertEquals(thousand, movement.getBalanceAfter());
        assertEquals(thousand, accountRepository.findById(1L).get().getBalance());
    }
}
