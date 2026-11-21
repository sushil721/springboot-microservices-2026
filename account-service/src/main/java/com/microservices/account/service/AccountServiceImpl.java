package com.microservices.account.service;

import com.microservices.account.dto.AccountsDto;
import com.microservices.account.dto.CustomerDto;
import com.microservices.account.entity.Accounts;
import com.microservices.account.entity.Customer;
import com.microservices.account.exception.CustomerAlreadyExistsException;
import com.microservices.account.exception.ResourceNotFoundException;
import com.microservices.account.mapper.AccountsMapper;
import com.microservices.account.mapper.CustomerMapper;
import com.microservices.account.repository.AccountsRepository;
import com.microservices.account.repository.CustomerRepository;
import com.microservices.account.utility.AccountUtility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService{

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;


    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number: "+customerDto.getMobileNumber());
        }
        //customer.setCreatedAt(LocalDateTime.now());
        //customer.setCreatedBy(customer.getName());

        Customer savedCustomer = customerRepository.save(customer);

        accountsRepository.save(AccountUtility.createNewAccount(savedCustomer));

    }

    @Override
    public CustomerDto fetchByMobileNumber(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Accounts", "Customer ID: ", customer.getCustomerId().toString()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdate = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto != null){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber())
                    .orElseThrow( () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString()));
            AccountsMapper.mapToAccounts(accountsDto, accounts);

            //accounts.setUpdatedAt(LocalDateTime.now());
            //accounts.setUpdatedBy(customerDto.getName());

            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow( ()-> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString()));

            CustomerMapper.mapToCustomer(customerDto, customer);
            //customer.setUpdatedAt(LocalDateTime.now());
            //customer.setUpdatedBy(customerDto.getName());

            customerRepository.save(customer);
            isUpdate = true;
        }
        return isUpdate;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow( ()-> new ResourceNotFoundException("Customer", "MobileNumber", mobileNumber));
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}
