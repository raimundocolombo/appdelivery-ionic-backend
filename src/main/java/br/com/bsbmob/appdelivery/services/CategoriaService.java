package br.com.bsbmob.appdelivery.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.bsbmob.appdelivery.domain.Categoria;
import br.com.bsbmob.appdelivery.repositories.CategoriaRepository;
import br.com.bsbmob.appdelivery.services.exception.DataIntegrityException;
import br.com.bsbmob.appdelivery.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		
		Categoria cat = repo.findOne(id);
		
		if (cat == null) {
			throw new ObjectNotFoundException("Categoria não encontrada! Id: " + id + ", Tipo: " + Categoria.class.getName());
		}
			
		return cat;
		
	}
	
	public Categoria inserir(Categoria obj) {
		obj.setId(null);
		
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possua produtos");
		}
	}
	
	public List<Categoria> findAll() {
		
		List<Categoria> list = repo.findAll();
		
		if (list == null || list.size() == 0) {
			throw new ObjectNotFoundException("Nenhuma Categoria não encontrada!, Tipo: " + Categoria.class.getName());
		}
			
		return list;
		
	}

}
