package br.com.bsbmob.appdelivery.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bsbmob.appdelivery.domain.Categoria;
import br.com.bsbmob.appdelivery.repositories.CategoriaRepository;
import br.com.bsbmob.appdelivery.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		
		Categoria cat = repo.findOne(id);
		
		if (cat == null) {
			throw new ObjectNotFoundException("Categoria não encontrada! Id: " + id + ", Tipo: " + Categoria.class.getName());
		}
			
		return cat;
		
	}

}
