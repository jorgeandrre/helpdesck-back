package com.jorge.helpdesck.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jorge.helpdesck.domain.Cliente;


public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
