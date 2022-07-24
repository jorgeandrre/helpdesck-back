package com.jorge.helpdesck.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jorge.helpdesck.domain.Chamado;
import com.jorge.helpdesck.domain.dtos.ChamadoDTO;
import com.jorge.helpdesck.services.ChamadoService;

@RestController
@RequestMapping(value = "/chamados") // enderpoint da requisição - informando para o acesso em chamados
public class ChamadoResource {
	
	@Autowired
	private ChamadoService service;
	
	
	@GetMapping(value = "/{id}")  // variavel de path para buscar a requisição por ID
	public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id){
		Chamado obj = service.findById(id);
		return ResponseEntity.ok().body(new ChamadoDTO(obj));
		
	}
	@GetMapping  // metodo para criar uma lista de todos os chamados, quando não chamados por ID findById
	public ResponseEntity<List<ChamadoDTO>> findAll(){
		List<Chamado> list = service.findAll() ;
		List<ChamadoDTO> listDTO = list.stream().map(obj -> new ChamadoDTO(obj)).collect(Collectors.toList());  // convertendo chamados para chamadosdto e gerando uma lista
	return ResponseEntity.ok().body(listDTO);
	}
	
@PostMapping	
public ResponseEntity<ChamadoDTO> create(@Valid @RequestBody ChamadoDTO objDTO){
	Chamado obj = service.create(objDTO);
	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
	return ResponseEntity.created(uri).build();
	
}
@PutMapping(value = "/{id}")
public ResponseEntity<ChamadoDTO> update( @PathVariable Integer id, @Valid @RequestBody ChamadoDTO objDTO){
	Chamado newObj = service.update(id, objDTO);
	return ResponseEntity.ok().body(new ChamadoDTO(newObj));
	
}
}



