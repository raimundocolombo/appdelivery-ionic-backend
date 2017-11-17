package br.com.bsbmob.appdelivery;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppDeliveryApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AppDeliveryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}

}