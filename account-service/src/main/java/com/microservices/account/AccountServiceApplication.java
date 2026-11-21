package com.microservices.account;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(
        info = @Info(
                title = "Account microservices REST API Documentation",
                description = "Sushil Account microservices REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Sushil Kumar Singh",
                        email = "sushilgiacr@gmail.com",
                        url = "https://www.sushil.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.com"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "External Sushil Account microservices REST API Documentation",
                url = "https://www.external.sushil.com"
        )
)

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

}
