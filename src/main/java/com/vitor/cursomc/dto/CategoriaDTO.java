package com.vitor.cursomc.dto;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.vitor.cursomc.domain.Categoria;

public class CategoriaDTO {
	
	// CategoriaDTO serve apenas para fazer a exibição no get, de somente o id e nome das categorias, sem exibir os produtos
	
	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5, max=80, message="O tamanho deve ser entre 5 e 80 caracteres")
	private String nome;
	
	public CategoriaDTO() {}
	
	public CategoriaDTO(Categoria obj) {
		id = obj.getId();
		nome = obj.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
