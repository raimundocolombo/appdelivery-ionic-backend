package br.com.bsbmob.appdelivery.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.bsbmob.appdelivery.domain.Cliente;
import br.com.bsbmob.appdelivery.domain.ItemPedido;
import br.com.bsbmob.appdelivery.domain.PagamentoComBoleto;
import br.com.bsbmob.appdelivery.domain.Pedido;
import br.com.bsbmob.appdelivery.domain.enums.EstadoPagamento;
import br.com.bsbmob.appdelivery.repositories.ClienteRepository;
import br.com.bsbmob.appdelivery.repositories.ItemPedidoRepository;
import br.com.bsbmob.appdelivery.repositories.PagamentoRepository;
import br.com.bsbmob.appdelivery.repositories.PedidoRepository;
import br.com.bsbmob.appdelivery.repositories.ProdutoRepository;
import br.com.bsbmob.appdelivery.security.UserSS;
import br.com.bsbmob.appdelivery.services.exception.AuthorizationException;
import br.com.bsbmob.appdelivery.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) {
		Pedido ped = repo.findOne(id);
		if (ped == null) {
			throw new ObjectNotFoundException("Pedido não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName());
		}
		return ped;
	}
	
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteRepository.findOne(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgto = (PagamentoComBoleto)obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pgto, obj.getInstante());
		}
		repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoRepository.findOne(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.save(obj.getItens());
		emailService.sendOrderConfirmationEmail(obj);
		return obj;
	}
	
	public Page<Pedido> findPage(Integer numPage, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado!");
		}
		Cliente cliente = clienteRepository.findOne(user.getId());
		PageRequest pageRequest = new PageRequest(numPage, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return repo.findBycliente(cliente, pageRequest);
	}

}
