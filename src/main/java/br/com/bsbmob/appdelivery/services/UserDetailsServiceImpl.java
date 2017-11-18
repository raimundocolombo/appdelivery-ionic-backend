package br.com.bsbmob.appdelivery.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.bsbmob.appdelivery.domain.Cliente;
import br.com.bsbmob.appdelivery.repositories.ClienteRepository;
import br.com.bsbmob.appdelivery.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cli = clienteRepository.findByEmail(email);
		if (cli == null) {
			throw new UsernameNotFoundException(email);
		}
		UserSS userSS = new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
		return userSS;
	}

}
