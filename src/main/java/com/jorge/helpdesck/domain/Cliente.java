package com.jorge.helpdesck.domain;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jorge.helpdesck.domain.dtos.ClienteDTO;
import com.jorge.helpdesck.domain.enums.Perfil;

@Entity
public class Cliente extends Pessoa{
	
	private static final long serialVersionUID = 1L;

	//Json ignorar as requisiçoes de chamados, retornar apenas as informaçoes do cliente
	@JsonIgnore
	@OneToMany(mappedBy = "cliente")
	private java.util.List<Chamado> chamados = new ArrayList<>();

public Cliente() {
	super();
	addPerfil(Perfil.CLIENTE);
}

public Cliente(Integer id, String nome, String cpf, String email, String senha) {
	super(id, nome, cpf, email, senha);
	addPerfil(Perfil.CLIENTE);
}

public Cliente(ClienteDTO obj) {
	super();
	this.id = obj.getId();
	this.nome = obj.getNome();
	this.cpf = obj.getCpf();
	this.email = obj.getEmail();
	this.senha = obj.getSenha();
	this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());  // perfil enumerado, precisa fazer a conversão
	this.dataCriacao = obj.getDataCriacao();
}

public java.util.List<Chamado> getChamados() {
	return chamados;
}

public void setChamados(java.util.List<Chamado> chamados) {
	this.chamados = chamados;
}




}