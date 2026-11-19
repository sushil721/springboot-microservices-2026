package com.microservices.account.controller;

import com.microservices.account.constant.AccountConstant;
import com.microservices.account.dto.CustomerDto;
import com.microservices.account.dto.ResponseDto;
import com.microservices.account.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( path = {"/v1/accounts"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class AccountController {

    private IAccountService accountService;

    @GetMapping("/ping")
    public ResponseEntity<String> ping(){
        return ResponseEntity.status(HttpStatus.OK).body("Hello, PONG");
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto){
        accountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstant.STATUS_201, AccountConstant.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchCustomerByMobileNumber(@RequestParam(value = "mobileNumber", required = true) String mobileNumber){

        CustomerDto customerDto = accountService.fetchByMobileNumber(mobileNumber);

        return ResponseEntity.status(HttpStatus.FOUND).body(customerDto);
    }
}
