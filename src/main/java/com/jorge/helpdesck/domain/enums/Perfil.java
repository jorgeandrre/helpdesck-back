package com.jorge.helpdesck.domain.enums;

public enum Perfil {
ADMIN(0, "ROLE_ADMIN"), CLIENTE(1, "ROLE_CLIENTE"), TECNICO(2, "ROLE_TECNICO"); // serve como um array para escolhaer o tipo de perfil do usuário

	private Integer codigo;
	private String descricao;
	private Perfil(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public String getDescricao() {
		return descricao;
	}

public static  Perfil toEnum(Integer cod) { //se esse codigo for igual a nulo ele vai retornar nulo
	if(cod == null) {
		return null;
	}
	for(Perfil x : Perfil.values()) {   // se perfil x for igual aos valores então retorna um perfil
		if(cod.equals(x.getCodigo())) {
			return x;
		}
	}
	throw new IllegalArgumentException("PERFIL INVÁLIDO");  // se não, lança uma exceção
}
}
