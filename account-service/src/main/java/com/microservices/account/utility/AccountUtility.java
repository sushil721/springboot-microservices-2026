package com.microservices.account.utility;

import com.microservices.account.constant.AccountConstant;
import com.microservices.account.entity.Accounts;
import com.microservices.account.entity.Customer;

import java.time.LocalDateTime;
import java.util.Random;

public class AccountUtility {

    private AccountUtility(){}

    public static Accounts createNewAccount(Customer customer){
        Accounts newAccount = new Accounts();

        long randomAccountNumber = 1000000000 + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccountNumber);
        newAccount.setCustomerId(customer.getCustomerId());
        newAccount.setAccountType(AccountConstant.SAVING);
        newAccount.setBranchAddress(AccountConstant.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy(customer.getName());
        return newAccount;
    }
}
