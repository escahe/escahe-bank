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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.escahe.bank.dto.mapper.IAccountRequestMapper;
import com.escahe.bank.dto.request.AccountRequest;
import com.escahe.bank.dto.response.AccountResponse;
import com.escahe.bank.model.Account;
import com.escahe.bank.model.Client;
import com.escahe.bank.service.IAccountsService;
import com.escahe.bank.service.IClientsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
class AccountController {

    private final IAccountsService accountsService;
    private final IClientsService clientsService;
    private final IAccountRequestMapper accountRequestMapper;

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAll() {
        List<AccountResponse> accounts = accountsService.getAllAccounts().stream()
            .map(AccountResponse::new).toList();

        if (accounts.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/id")
    public ResponseEntity<AccountResponse> getById(@RequestParam("id") Long id) {
        AccountResponse accountResponse = new AccountResponse(accountsService.getAccountById(id));
        return ResponseEntity.ok(accountResponse);
    }

    @PostMapping
    public ResponseEntity<AccountResponse> create(@RequestBody AccountRequest accountRequest) {
        Client client = clientsService.getClientById(accountRequest.getClientId());
        Account account = accountRequestMapper.toAccount(accountRequest);
        account.setClient(client);
        AccountResponse savedAccount = new AccountResponse(accountsService.saveAccount(account));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> fullAccountUpdate(@PathVariable("id") Long id,
    @RequestBody AccountRequest accountRequest) {
        Account account = accountRequestMapper.toAccount(accountRequest);
        AccountResponse accountResponse = new AccountResponse(accountsService.fullAccountUpdate(account, id));
        return ResponseEntity.ok(accountResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AccountResponse> partialUpdateAccount(@PathVariable("id") Long id,
    @RequestBody AccountRequest accountRequest) {
        Account account = accountRequestMapper.toAccount(accountRequest);
        AccountResponse accountResponse = new AccountResponse(accountsService.partialUpdateAccount(account, id));
        return ResponseEntity.ok(accountResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AccountResponse> delete(@PathVariable("id") Long id) {
        AccountResponse accountResponse = new AccountResponse(accountsService.deleteAccount(id));
        return ResponseEntity.ok(accountResponse);
    }
}