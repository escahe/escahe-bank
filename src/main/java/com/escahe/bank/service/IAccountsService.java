package com.escahe.bank.service;

import java.util.List;

import com.escahe.bank.model.Account;

public interface IAccountsService {
    List<Account> getAllAccounts();
    Account getAccountById(Long id);
    Account saveAccount(Account account);
    Account partialUpdateAccount(Account account, Long id);
    Account fullAccountUpdate(Account account, Long id);
    Account deleteAccount(Long id);
}
