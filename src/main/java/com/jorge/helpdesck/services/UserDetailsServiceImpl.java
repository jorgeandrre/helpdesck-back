package com.jorge.helpdesck.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jorge.helpdesck.domain.Pessoa;
import com.jorge.helpdesck.repositories.PessoaRepository;
import com.jorge.helpdesck.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private PessoaRepository repository;
	//metodo para carregar usuario pelo seu usarname que será o email
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { 
		Optional<Pessoa>user = repository.findByEmail(email);
		if(user.isPresent()) { // se existir esse perfil no banco
			return new UserSS(user.get().getId(), user.get().getEmail(), user.get().getSenha(), user.get().getPerfis());
		}
	  throw new UsernameNotFoundException(email); // se não existir lança a exceção
	}

}
