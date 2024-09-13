package com.cashdo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
	    info = @Info(
	        title = "CASHDO Bank App",
	        version = "v1.0.0",
	        description = "Backend Rest API for CASHDO Bank App",
	        license = @License(
	            name = "N Raja Gopal Reddy",
	            url = "https://github.com/rajagopal-reddy"
	        ),
	        contact = @Contact(
	            name = "N Raja Gopal Reddy",
	            email = "rajagopalreddy337@gmail.com",
	            url = "https://github.com/rajagopal-reddy" 
	        )
	    ),
	    externalDocs = @ExternalDocumentation(
	        url = "https://github.com/rajagopal-reddy",
	        description = "CASHDO Bank App Description"
	    )
	)

public class CashdoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashdoApplication.class, args);
	}

}
