package com.carbigdata.api_ocorrencia.model.enumeration;

import java.util.Objects;

public enum StatusOcorrenciaEnum {
	
	ATIVO(1l),
	FINALIZADO(2l);

	StatusOcorrenciaEnum(Long id) {
		this.id =id;
	}
	
	private final Long id;
	
	   public static StatusOcorrenciaEnum getCategoriaDocumento(Long valor) {
	        for (StatusOcorrenciaEnum tipo : values()) {
	            if (Objects.equals(tipo.id, valor)) {
	                return tipo;
	            }
	        }
	        throw new IllegalArgumentException("Valor inválido para StatusOcorrenciaEnum: " + valor);
	    }

}
