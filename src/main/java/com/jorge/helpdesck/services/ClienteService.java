package com.jorge.helpdesck.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jorge.helpdesck.domain.Cliente;
import com.jorge.helpdesck.domain.Pessoa;
import com.jorge.helpdesck.domain.dtos.ClienteDTO;
import com.jorge.helpdesck.repositories.ClienteRepository;
import com.jorge.helpdesck.repositories.PessoaRepository;
import com.jorge.helpdesck.services.exceptions.DataIntegrityViolationException;
import com.jorge.helpdesck.services.exceptions.ObjectnotFoundException;

@Service
public class ClienteService {
@Autowired
private ClienteRepository repository;
@Autowired
private PessoaRepository pessoaRepository;
@Autowired
private BCryptPasswordEncoder encoder;

public Cliente findById(Integer id) {
    Optional<Cliente> obj = repository.findById(id);
      return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado na BASE DE DADOS - Tente novamente"));
}

public List<Cliente> findAll() {
	return repository.findAll();
}

//metodo para fazer update
public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
	objDTO.setId(id);
	Cliente oldObj = findById(id);    //instancia para validar se já existe cpf ou email cadastrado antes de fazer o update
	validaPorCpfEEmail(objDTO);
	oldObj = new Cliente(objDTO); // recebe o novo objeto
	
	return repository.save(oldObj); //retorna o novo objeto
}
//metodo para delete
public void delete(Integer id) {
	Cliente obj = findById(id);  // verificar se o id existe no banco
	if(obj.getChamados().size() > 0) {   // criando uma exceção para não deletar clientes com chamados em aberto

		throw new DataIntegrityViolationException("O cliente possui ordens de serviço em aberto - não pode ser EXCLUIDO");
	} 

	repository.deleteById(id);  // lança exceçção
	
}

public Cliente create(ClienteDTO objDTO) {
	objDTO.setId(null); // para não alterar o id de requisição no banco
	objDTO.setSenha(encoder.encode(objDTO.getSenha())); // metodo para esconder senha no banco de dados
	validaPorCpfEEmail(objDTO);
	Cliente newObj = new Cliente(objDTO);
	return repository.save(newObj);
}
//metodo para validar cpf e email
private void validaPorCpfEEmail(ClienteDTO objDTO) {
	Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
		throw new DataIntegrityViolationException("CPF JÁ CADASTRADO NO SISTEMA");
	
	
}
		obj = pessoaRepository.findByEmail(objDTO.getEmail());  // SE CPF PASSAR POR VALIDAÇÃO, CAI NA VALIDAÇÃO DE EMAIL
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("E-MAIL JÁ CADASTRADO NO SISTEMA");


}
}


}




