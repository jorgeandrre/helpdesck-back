package com.jorge.helpdesck.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jorge.helpdesck.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
  
	Optional<Pessoa> findByCpf(String cpf);      //evitar duplicar cpf
	Optional<Pessoa> findByEmail(String email);  //evitar duplicar email
}
