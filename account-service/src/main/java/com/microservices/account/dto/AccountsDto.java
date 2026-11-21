package com.microservices.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Accounts Information"
)
public class AccountsDto {

    @Schema(
            description = "Account Number of Accounts",
            example = "3211234567"
    )
    @NotEmpty(message = "Account Number can not be Empty OR Null.")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account Number must be 10 digits.")
    private Long accountNumber;

    @Schema(
            description = "Account Type of Accounts",
            example = "Saving"
    )
    @NotEmpty(message = "Account Type can not be Empty OR Null.")
    private String accountType;

    @Schema(
            description = "Branch Address of Accounts",
            example = "1D, xyz, Hyderabad, India - 5300032"
    )
    @NotEmpty(message = "Branch Address can not be Empty OR Null.")
    private String branchAddress;
}
