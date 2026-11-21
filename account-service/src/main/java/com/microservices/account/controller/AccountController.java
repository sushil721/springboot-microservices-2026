package com.microservices.account.controller;

import com.microservices.account.constant.AccountConstant;
import com.microservices.account.dto.CustomerDto;
import com.microservices.account.dto.ResponseDto;
import com.microservices.account.service.IAccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( path = {"/v1/accounts"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class AccountController {

    private IAccountService iAccountService;

    @GetMapping("/ping")
    public ResponseEntity<String> ping(){
        return ResponseEntity.status(HttpStatus.OK).body("Hello, PONG");
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody @Valid CustomerDto customerDto){
        iAccountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstant.STATUS_201, AccountConstant.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchCustomerByMobileNumber(
            @RequestParam(value = "mobileNumber", required = true)
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits.")
            String mobileNumber){

        CustomerDto customerDto = iAccountService.fetchByMobileNumber(mobileNumber);

        return ResponseEntity.status(HttpStatus.FOUND).body(customerDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@RequestBody @Valid CustomerDto customerDto){
        boolean isUpdated = iAccountService.updateAccount(customerDto);
        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstant.STATUS_200, AccountConstant.MESSAGE_200));
        }else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstant.STATUS_417, AccountConstant.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(
            @RequestParam(value = "mobileNumber", required = true)
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits.")
            String mobileNumber){

        boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstant.STATUS_200, AccountConstant.MESSAGE_200));
        }else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountConstant.STATUS_417, AccountConstant.MESSAGE_417_DELETE));
        }
    }
}
