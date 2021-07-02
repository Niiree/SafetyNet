package com.SafetyNet.Safety;

import com.SafetyNet.Safety.util.ImportData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;

@SpringBootApplication
@EnableOpenApi
public class SafetyApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SafetyApplication.class, args);
		//importData.load();
	}

}
