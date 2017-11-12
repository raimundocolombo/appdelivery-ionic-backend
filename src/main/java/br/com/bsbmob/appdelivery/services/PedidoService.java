package br.com.bsbmob.appdelivery.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bsbmob.appdelivery.domain.Pedido;
import br.com.bsbmob.appdelivery.repositories.PedidoRepository;
import br.com.bsbmob.appdelivery.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	public Pedido buscar(Integer id) {
		
		Pedido ped = repo.findOne(id);
		
		if (ped == null) {
			throw new ObjectNotFoundException("Pedido não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName());
		}
			
		return ped;
		
	}

}
