package com.jorge.helpdesck.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jorge.helpdesck.domain.Chamado;


public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

}
