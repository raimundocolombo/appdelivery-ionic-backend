package br.com.bsbmob.appdelivery;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.bsbmob.appdelivery.domain.Categoria;
import br.com.bsbmob.appdelivery.repositories.CategoriaRepository;

@SpringBootApplication
public class AppDeliveryApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public static void main(String[] args) {
		SpringApplication.run(AppDeliveryApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		Categoria cat1 = new Categoria(null, "Combos");
		Categoria cat2 = new Categoria(null, "Potes");
		
		categoriaRepository.save(Arrays.asList(cat1, cat2));
	}
}