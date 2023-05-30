package com.escahe.bank.dto.mapper.implementation;

import org.springframework.stereotype.Component;

import com.escahe.bank.dto.mapper.IAccountRequestMapper;
import com.escahe.bank.dto.request.AccountRequest;
import com.escahe.bank.model.Account;
import com.escahe.bank.model.Client;

@Component
public class AccountRequestMapperImp implements IAccountRequestMapper {

    @Override
    public Account toAccount(AccountRequest accountRequest) {
        return new Account(
            accountRequest.getId(),
            accountRequest.getNumber(),
            accountRequest.getType(),
            accountRequest.getBalance(),
            accountRequest.getStatus(),
            new Client(accountRequest.getClientId(), null, null, null, null, null, null, null, null)
        );
    }
    
}
