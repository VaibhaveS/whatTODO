package com.example.done;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

@SpringBootApplication
public class DoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoneApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
