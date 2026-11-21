package com.microservices.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "ErrorResponseDto",
        description = "Schema to hold ErrorResponseDto Information"
)
@Data
@AllArgsConstructor
public class ErrorResponseDto {

    @Schema(
            description = "API Path of ErrorResponseDto",
            example = "uri=/v1/accounts/update"
    )
    private String apiPath;

    @Schema(
            description = "Error Code of ErrorResponseDto",
            example = "404 NOT_FOUND"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error Message of ErrorResponseDto",
            example = "Account not found with the given input data AccountNumber: '1314031662'"
    )
    private String errorMessage;

    @Schema(
            description = "Error Time of ErrorResponseDto",
            example = "2026-11-22T02:31:28.6501673"
    )
    private LocalDateTime errorTime;
}
