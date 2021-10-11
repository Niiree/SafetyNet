package com.SafetyNet.Safety;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableOpenApi
@EnableSwagger2
public class SafetyApplication {

    public static void main(String[] args)   {
        SpringApplication.run(SafetyApplication.class, args);
    }

}
