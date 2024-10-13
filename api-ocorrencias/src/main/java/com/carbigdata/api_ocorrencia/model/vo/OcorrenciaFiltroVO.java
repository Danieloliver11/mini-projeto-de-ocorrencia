package com.carbigdata.api_ocorrencia.model.vo;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OcorrenciaFiltroVO {

	private String nome;
	private String cpf;
	private LocalDate data;
	private String cidade;
	
	@JsonIgnore
	public boolean isPossuiNome() {
		return nome != null && !nome.isBlank();
	}
	
	@JsonIgnore
	public boolean isPossuiData() {
		return data != null;
	}
	
	@JsonIgnore
	public boolean isPossuiCpf() {
		return cpf != null && !cpf.isBlank();
	}
	
	@JsonIgnore
	public boolean isPossuiCidade() {
		return cidade != null && !cidade.isBlank();
	}

}
