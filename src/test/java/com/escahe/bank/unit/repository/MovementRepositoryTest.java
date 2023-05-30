package com.escahe.bank.unit.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.escahe.bank.model.Account;
import com.escahe.bank.model.Client;
import com.escahe.bank.model.Movement;
import com.escahe.bank.repository.AccountRepository;
import com.escahe.bank.repository.ClientRepository;
import com.escahe.bank.repository.MovementRepository;

@DataJpaTest
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class MovementRepositoryTest {

    @Autowired
    MovementRepository movementRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;
    
    @Test
    public void totalWithdrawnToday() {
        Client client = new Client(1L, null, null, null, null, null, null, null, null);
        Account account = new Account(1L, "1234567890", null, null, null, client);
        clientRepository.save(client);
        accountRepository.save(account);

        BigDecimal totalWithdrawalsToday = new BigDecimal("-800.00");
        BigDecimal totalWithdrawalsYesterday = new BigDecimal("-1000.00");

        for (int i = 0; i < 4; i++) {
            Movement yesterdayWithdrawal = new Movement(
                LocalDateTime.now().minusDays(1),
                BigDecimal.valueOf(-250),
                null,
                account
            );
            Movement todayWithdrawal = new Movement(
                LocalDateTime.now(),
                BigDecimal.valueOf(-200),
                null,
                account
            );
            movementRepository.save(yesterdayWithdrawal);
            movementRepository.save(todayWithdrawal);
        }
        
        assertEquals(totalWithdrawalsToday, movementRepository.totalWithdrawnTodayByAccount(account.getNumber()));
        assertNotEquals(totalWithdrawalsYesterday, movementRepository.totalWithdrawnTodayByAccount(account.getNumber()));
    }

}
