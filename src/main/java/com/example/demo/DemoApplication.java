package com.example.demo;

import com.example.demo.usecase.AdjustValues;
import com.example.demo.usecase.OutputResults;
import com.example.demo.usecase.ReadFile;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class DemoApplication implements CommandLineRunner {

	private ReadFile readFile;
	private AdjustValues adjustValues;
	private OutputResults outputResults;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... strings) {
	    try {
            readFile.execute();
            adjustValues.execute();
            outputResults.execute();
        } catch (Exception ex) {
	        ex.printStackTrace();
        }
	}
}
