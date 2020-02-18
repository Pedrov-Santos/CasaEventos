package com.example.demo.model;

public enum StatusSelecionarGenero {
	
	PAGODE("Pagode"),
	FORRO("Forró"),
	ELETRONICA("Eletronica"),
	FUNK("Funk"),
	SERTANEJO("Sertanejo");
	
private String descricao;
	
	StatusSelecionarGenero(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
