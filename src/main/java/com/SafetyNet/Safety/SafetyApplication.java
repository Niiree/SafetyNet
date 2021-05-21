package com.SafetyNet.Safety;

import com.SafetyNet.Safety.util.ImportData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class SafetyApplication {

	//private static ImportData importData;

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SafetyApplication.class, args);
		//importData.load();
	}

}
