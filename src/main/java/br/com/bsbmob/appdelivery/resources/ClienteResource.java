package br.com.bsbmob.appdelivery.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.bsbmob.appdelivery.domain.Cliente;
import br.com.bsbmob.appdelivery.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> listar(@PathVariable Integer id) {
		
		Cliente cat1 = service.buscar(id);
		
		return ResponseEntity.ok().body(cat1);
	}

}
