package com.jorge.helpdesck.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jorge.helpdesck.domain.Chamado;
import com.jorge.helpdesck.domain.Cliente;
import com.jorge.helpdesck.domain.Tecnico;
import com.jorge.helpdesck.domain.enums.Perfil;
import com.jorge.helpdesck.domain.enums.Prioridade;
import com.jorge.helpdesck.domain.enums.Status;
import com.jorge.helpdesck.repositories.ChamadoRepository;
import com.jorge.helpdesck.repositories.ClienteRepository;
import com.jorge.helpdesck.repositories.TecnicoRepository;

@Service
public class DBService {
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ChamadoRepository chamadoRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public void instanciaDB() {
		Tecnico tec1 = new Tecnico(null, "Jorge André", "02102203245", "a@gmail.com", encoder.encode("123"));  //encoder.encode para não mostrar senha no banco
		tec1.addPerfil(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null, "Neymar Junior", "12354654533", "b@gmail.com", encoder.encode("123"));
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "primeiro chamado", tec1, cli1);
		
				tecnicoRepository.saveAll(Arrays.asList(tec1));
				clienteRepository.saveAll(Arrays.asList(cli1));
				chamadoRepository.saveAll(Arrays.asList(c1));
				
				Tecnico tec2 = new Tecnico(null, "Daniel", "02102203215", "ad@gmail.com", encoder.encode("123"));
				tec1.addPerfil(Perfil.ADMIN);
				Cliente cli2 = new Cliente(null, "Lucena", "12351654513", "ba@gmail.com", encoder.encode("123"));
				Chamado c2 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 02", "primeiro chamado", tec1, cli1);
				
						tecnicoRepository.saveAll(Arrays.asList(tec2));
						clienteRepository.saveAll(Arrays.asList(cli2));
						chamadoRepository.saveAll(Arrays.asList(c2));
}

}