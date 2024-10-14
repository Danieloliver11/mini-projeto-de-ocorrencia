package com.carbigdata.api_ocorrencia.model.enumeration;

import java.util.Objects;

public enum StatusOcorrenciaEnum  {
	
	ATIVO(1),
	FINALIZADO(2);

	StatusOcorrenciaEnum(int id) {
		this.id =id;
	}
	
	private final int id;
	
	   public static StatusOcorrenciaEnum getCategoriaDocumento(int valor) {
	        for (StatusOcorrenciaEnum tipo : values()) {
	            if (Objects.equals(tipo.id, valor)) {
	                return tipo;
	            }
	        }
	        throw new IllegalArgumentException("Valor inválido para StatusOcorrenciaEnum: " + valor);
	    }

}
