package com.jorge.helpdesck.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jorge.helpdesck.domain.Pessoa;
import com.jorge.helpdesck.domain.Tecnico;
import com.jorge.helpdesck.domain.dtos.TecnicoDTO;
import com.jorge.helpdesck.repositories.PessoaRepository;
import com.jorge.helpdesck.repositories.TecnicoRepository;
import com.jorge.helpdesck.services.exceptions.DataIntegrityViolationException;
import com.jorge.helpdesck.services.exceptions.ObjectnotFoundException;

@Service
public class TecnicoService {
@Autowired
private TecnicoRepository repository;

@Autowired
private PessoaRepository pessoaRepository;

public Tecnico findById(Integer id) {
    Optional<Tecnico> obj = repository.findById(id);
      return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado na BASE DE DADOS - Tente novamente"));
}

public List<Tecnico> findAll() {
	return repository.findAll();
}

//metodo para fazer update
public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
	objDTO.setId(id);
	Tecnico oldObj = findById(id);    //instancia para validar se já existe cpf ou email cadastrado antes de fazer o update
	validaPorCpfEEmail(objDTO);
	oldObj = new Tecnico(objDTO); // recebe o novo objeto
	
	return repository.save(oldObj); //retorna o novo objeto
}
//metodo para delete
public void delete(Integer id) {
	Tecnico obj = findById(id);  // verificar se o id existe no banco
	if(obj.getChamados().size() > 0) {   // criando uma exceção para não deletar tecnicos com chamados em aberto

		throw new DataIntegrityViolationException("O técnico possui CHAMADOS em aberto - não pode ser EXCLUIDO");
	} 

	repository.deleteById(id);  // lança exceçção
	
}

public Tecnico create(TecnicoDTO objDTO) {
	objDTO.setId(null); // para não alterar o id de requisição no banco
	validaPorCpfEEmail(objDTO);
	Tecnico newObj = new Tecnico(objDTO);
	return repository.save(newObj);
}
//metodo para validar cpf e email
private void validaPorCpfEEmail(TecnicoDTO objDTO) {
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




