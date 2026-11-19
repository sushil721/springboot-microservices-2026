package com.microservices.account.mapper;

import com.microservices.account.constant.AccountConstant;
import com.microservices.account.dto.AccountsDto;
import com.microservices.account.entity.Accounts;

public class AccountsMapper {

    private AccountsMapper(){}

    public static AccountsDto mapToAccountsDto(Accounts accounts, AccountsDto accountsDto){
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());

        return accountsDto;
    }

    public static Accounts mapToAccounts(AccountsDto accountsDto, Accounts accounts){
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());

        return accounts;
    }
}
