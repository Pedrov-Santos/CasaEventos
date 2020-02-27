package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItensCompras {

	@ManyToOne
	private NovoShow show;
	
	@ManyToOne
	private Compra compra;
	
	private Integer quantidade =0;
	
	private Double valorUnitario=0.;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long codigo;
	
	private Double valorTotal=0.;

	public NovoShow getShow() {
		return show;
	}

	public void setShow(NovoShow show) {
		this.show = show;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	
}
