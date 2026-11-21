package com.microservices.account.controller;

import com.microservices.account.constant.AccountConstant;
import com.microservices.account.dto.CustomerDto;
import com.microservices.account.dto.ErrorResponseDto;
import com.microservices.account.dto.ResponseDto;
import com.microservices.account.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST API for Account in SushilBank",
        description = "CRUD REST API for Account in SushilBank to CREATE, FETCH, UPDATE, and DELETE account details."
)

@RestController
@RequestMapping( path = {"/v1/accounts"}, produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class AccountController {

    private IAccountService iAccountService;

    @Operation(
            summary = "Ping Account Application",
            description = "Ping Account Application inside SushilBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Hello, PONG"
            )
    })
    @GetMapping("/ping")
    public ResponseEntity<String> ping(){
        return ResponseEntity.status(HttpStatus.OK).body("Hello, PONG");
    }

    @Operation(
            summary = "CREATE Account Rest API",
            description = "Rest API to create new Customer and Accounts inside SushilBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponseDto.class
                            )
                    )
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody @Valid CustomerDto customerDto){
        iAccountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstant.STATUS_201, AccountConstant.MESSAGE_201));
    }


    @Operation(
            summary = "FETCH Account Rest API By Correct Mobile Number",
            description = "Rest API to fetch Customer and Accounts details By Correct Mobile Number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "302",
                    description = "HTTP Status FOUND"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status NOT_FOUND"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponseDto.class
                            )
                    )
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchCustomerByMobileNumber(
            @RequestParam(value = "mobileNumber", required = true)
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits.")
            String mobileNumber){

        CustomerDto customerDto = iAccountService.fetchByMobileNumber(mobileNumber);

        return ResponseEntity.status(HttpStatus.FOUND).body(customerDto);
    }

    @Operation(
            summary = "UPDATE Account Rest API",
            description = "Rest API to UPDATE Customer and Accounts inside SushilBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status UPDATE"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status EXPECTATION_FAILED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponseDto.class
                            )
                    )
            )
    })
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


    @Operation(
            summary = "DELETE Account Rest API By Correct Mobile Number",
            description = "Rest API to DELETE Customer and Accounts By Correct Mobile Number inside SushilBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status DELETED"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status EXPECTATION_FAILED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponseDto.class
                            )
                    )
            )
    })
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
