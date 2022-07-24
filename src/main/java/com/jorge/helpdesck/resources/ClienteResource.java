package com.jorge.helpdesck.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jorge.helpdesck.domain.Cliente;
import com.jorge.helpdesck.domain.dtos.ClienteDTO;
import com.jorge.helpdesck.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
//localhost:8080/clientes
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	//buscar todas as chamadas dasrequisições técnicos por id //localhost:8080/clientes/1
	@GetMapping(value = "/{id}")
	//representar todas as respostas HTTP
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id){
		Cliente obj = this.service.findById(id);
		return ResponseEntity.ok().body(new ClienteDTO(obj));
}
	
	// criar o enderpoint de todos os tecnicos
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll(){
		List<Cliente> list = service.findAll();
		List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList()); //convertendo Cliente para cliente DTO
	     return ResponseEntity.ok().body(listDTO); // RETORNAR O ENDPOINT IMPLEMENTADO
}
@PostMapping	
public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO objDTO){
	Cliente newObj = service.create(objDTO); // CRIANDO UMA NOVA INTANCIA PARA ENDERPOINT
	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri(); //comando para converter a nova URL
		return ResponseEntity.created(uri).build();

}

//fazer update de cliente
@PutMapping(value = "/{id}")
public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDTO){
	Cliente obj = service.update(id, objDTO);
	return ResponseEntity.ok().body(new ClienteDTO(obj)); //RETORNA UM NOVO OBJETO 
}

//deletar dados do banco
@DeleteMapping(value = "/{id}") // exclusão por id passando o @PathVariable
public ResponseEntity<ClienteDTO> delete(@PathVariable Integer id){
	service.delete(id);
	return ResponseEntity.noContent().build();

}
}
