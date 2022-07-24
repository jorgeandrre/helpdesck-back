package com.jorge.helpdesck.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jorge.helpdesck.domain.Tecnico;


public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}
