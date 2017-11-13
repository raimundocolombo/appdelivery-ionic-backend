package br.com.bsbmob.appdelivery.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.bsbmob.appdelivery.domain.Cliente;
import br.com.bsbmob.appdelivery.dto.ClienteDTO;
import br.com.bsbmob.appdelivery.repositories.ClienteRepository;
import br.com.bsbmob.appdelivery.services.exception.DataIntegrityException;
import br.com.bsbmob.appdelivery.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Cliente cli = repo.findOne(id);
		if (cli == null) {
			throw new ObjectNotFoundException("Cliente não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName());
		}
		return cli;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir,pois há dados relacionados.");
		}
	}
	
	public List<Cliente> findAll() {
		
		List<Cliente> list = repo.findAll();
		
		if (list == null || list.size() == 0) {
			throw new ObjectNotFoundException("Nenhuma Cliente não encontrada!, Tipo: " + Cliente.class.getName());
		}
			
		return list;
	}
	
	public Page<Cliente> findPage(Integer numPage, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(numPage, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDto(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
