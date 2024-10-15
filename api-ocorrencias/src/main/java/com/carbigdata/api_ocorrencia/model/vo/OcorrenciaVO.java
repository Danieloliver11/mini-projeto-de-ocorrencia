package com.carbigdata.api_ocorrencia.model.vo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record OcorrenciaVO(
		Long id,
		@NotBlank(message = "Nome é obrigatório")
		String nome ,
		@NotBlank(message = "Cpf é obrigatório")
		String cpf,
		@Valid
		EnderecoVO endereco,
		String nomeArquivos
		) {

}
