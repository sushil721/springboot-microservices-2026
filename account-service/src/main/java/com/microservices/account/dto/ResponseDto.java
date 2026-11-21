package com.microservices.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(
        name = "ResponseDto",
        description = "Schema to hold ResponseDto Information"
)
@Data
@AllArgsConstructor
public class ResponseDto {

    @Schema(
            description = "API Path of ResponseDto",
            example = "201"
    )
    private String statusCode;

    @Schema(
            description = "API Path of ResponseDto",
            example = "Account created successfully."
    )
    private String statusMsg;
}
