package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class CasaShow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotNull
	@NotEmpty(message = "Endereço obrigatorio.")
	@Size(max = 75, message = "O endereço não pode ultrapassar 75 caracteres.")
	@Size(min=10 , message = "O endeço deve ter no minímo 10 caracteres.")
	private String enderecoCasa;
	
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@NotNull(message = "Numero Obrigatório.")
	private int numeroCasa;
	
	@NotEmpty(message = "Cidade obrigatoria.")
	@Size(max = 35 , message = "A cidade não pode conter mais de 35 caracteres.")
	private String cidadeCasa;
	
	@NotEmpty(message = "Nome da casa obrigatorio.")
	@Size(max = 50 , message = "O nome da casa não pode conter mais de 35 caracteres.")
	private String nomeCasa;

	public String getEnderecoCasa() {
		return enderecoCasa;
	}

	public void setEnderecoCasa(String enderecoCasa) {
		this.enderecoCasa = enderecoCasa;
	}

	public int getNumeroCasa() {
		return numeroCasa;
	}

	public void setNumeroCasa(int numeroCasa) {
		this.numeroCasa = numeroCasa;
	}

	public String getCidadeCasa() {
		return cidadeCasa;
	}

	public void setCidadeCasa(String cidadeCasa) {
		this.cidadeCasa = cidadeCasa;
	}

	public String getNomeCasa() {
		return nomeCasa;
	}

	public void setNomeCasa(String nomeCasa) {
		this.nomeCasa = nomeCasa;
	}

	

	
}
