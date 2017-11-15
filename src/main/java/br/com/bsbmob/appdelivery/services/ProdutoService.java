package br.com.bsbmob.appdelivery.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.bsbmob.appdelivery.domain.Categoria;
import br.com.bsbmob.appdelivery.domain.Produto;
import br.com.bsbmob.appdelivery.repositories.CategoriaRepository;
import br.com.bsbmob.appdelivery.repositories.ProdutoRepository;
import br.com.bsbmob.appdelivery.services.exception.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto find(Integer id) {
		Produto ped = repo.findOne(id);
		if (ped == null) {
			throw new ObjectNotFoundException("Produto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName());
		}
		return ped;
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer numPage, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(numPage, linesPerPage, Direction.valueOf(direction), orderBy);
		
		List<Categoria> categorias = categoriaRepository.findAll(ids);
		
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
		
	}

}
