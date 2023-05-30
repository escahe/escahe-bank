package com.escahe.bank.dto.mapper;

import com.escahe.bank.dto.request.AccountRequest;
import com.escahe.bank.model.Account;

public interface IAccountRequestMapper {
    
    Account toAccount(AccountRequest accountRequest);
}
