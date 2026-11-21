package com.microservices.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer Information"
)
public class CustomerDto {

    @Schema(
            description = "Name of Customer",
            example = "Sushil"
    )
    @NotEmpty(message = "Customer name can not be Empty OR Null.")
    @Size(min = 5, max = 30, message = "The length of customer name should be between 5 to 30.")
    private String name;

    @Schema(
            description = "Email of Customer",
            example = "sushil@mail.com"
    )
    @NotEmpty(message = "Customer email can not be Empty OR Null.")
    @Email(message = "Email address should be a valid value.")
    private String email;

    @Schema(
            description = "Mobile Number of Customer",
            example = "3421567890"
    )
    @NotEmpty(message = "Customer mobile number can not be Empty OR Null.")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits.")
    private String mobileNumber;

    @Schema(
            description = "Account Details of Customer"
    )
    private AccountsDto accountsDto;
}
