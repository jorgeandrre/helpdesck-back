package com.jorge.helpdesck.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jorge.helpdesck.domain.Tecnico;
import com.jorge.helpdesck.domain.dtos.TecnicoDTO;
import com.jorge.helpdesck.services.TecnicoService;

@RestController
@RequestMapping(value = "/tecnicos")
//localhost:8080/tecnicos
public class TecnicoResource {

	@Autowired
	private TecnicoService service;
	
	//buscar todas as chamadas dasrequisições técnicos por id //localhost:8080/tecnicos/1
	@GetMapping(value = "/{id}")
	//representar todas as respostas HTTP
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id){
		Tecnico obj = this.service.findById(id);
		return ResponseEntity.ok().body(new TecnicoDTO(obj));
}
	
	// criar o enderpoint de todos os tecnicos
	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll(){
		List<Tecnico> list = service.findAll();
		List<TecnicoDTO> listDTO = list.stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList()); //convertendo Tecnico para tecnico DTO
	     return ResponseEntity.ok().body(listDTO); // RETORNAR O ENDPOINT IMPLEMENTADO
}

@PreAuthorize("hasAnyRole(ADMIN)")  //paramanipular alterações precisa ser admin
@PostMapping	
public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO){
	Tecnico newObj = service.create(objDTO); // CRIANDO UMA NOVA INTANCIA PARA ENDERPOINT
	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri(); //comando para converter a nova URL
		return ResponseEntity.created(uri).build();

}

//fazer update de tecnico
@PreAuthorize("hasAnyRole(ADMIN)")  //paramanipular alterações precisa ser admin
@PutMapping(value = "/{id}")
public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO){
	Tecnico obj = service.update(id, objDTO);
	return ResponseEntity.ok().body(new TecnicoDTO(obj)); //RETORNA UM NOVO OBJETO 
}

//deletar dados do banco
@PreAuthorize("hasAnyRole(ADMIN)")  //paramanipular alterações precisa ser admin
@DeleteMapping(value = "/{id}") // exclusão por id passando o @PathVariable
public ResponseEntity<TecnicoDTO> delete(@PathVariable Integer id){
	service.delete(id);
	return ResponseEntity.noContent().build();

}
}
