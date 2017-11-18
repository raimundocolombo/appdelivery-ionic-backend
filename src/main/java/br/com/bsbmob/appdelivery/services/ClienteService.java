package br.com.bsbmob.appdelivery.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.bsbmob.appdelivery.domain.Cidade;
import br.com.bsbmob.appdelivery.domain.Cliente;
import br.com.bsbmob.appdelivery.domain.Endereco;
import br.com.bsbmob.appdelivery.domain.enums.TipoCliente;
import br.com.bsbmob.appdelivery.dto.ClienteDTO;
import br.com.bsbmob.appdelivery.dto.ClienteNewDTO;
import br.com.bsbmob.appdelivery.repositories.CidadeRepository;
import br.com.bsbmob.appdelivery.repositories.ClienteRepository;
import br.com.bsbmob.appdelivery.repositories.EnderecoRepository;
import br.com.bsbmob.appdelivery.services.exception.DataIntegrityException;
import br.com.bsbmob.appdelivery.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCrypt;
	
	public Cliente find(Integer id) {
		Cliente cli = repo.findOne(id);
		if (cli == null) {
			throw new ObjectNotFoundException("Cliente não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName());
		}
		return cli;
	}
	
	public Cliente inserir(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.save(obj.getEnderecos());
		return obj;
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
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}
	
	public Cliente fromDto(ClienteNewDTO objDto) {
		Cliente cliente = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), bCrypt.encode(objDto.getSenha()));
		Cidade cidade = cidadeRepository.findOne(objDto.getCidadeId());
		Endereco endereco = new Endereco(null,objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cliente, cidade);
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2() != null) {
			cliente.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3() != null) {
			cliente.getTelefones().add(objDto.getTelefone3());
		}
		
		return cliente;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
